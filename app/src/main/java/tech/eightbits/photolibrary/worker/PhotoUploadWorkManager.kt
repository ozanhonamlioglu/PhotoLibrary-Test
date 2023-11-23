package tech.eightbits.photolibrary.worker

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import tech.eightbits.photolibrary.R
import java.lang.IllegalStateException

class PhotoUploadWorkManager(
    private val context: Context,
    private val parameters: WorkerParameters,
) : CoroutineWorker(context, parameters) {
    private var totalImages = 0
    private var uploadCount = 1

    override suspend fun doWork(): Result {
        val stringUrls = parameters.inputData.getStringArray(KEY_URI_ARRAY)

        val uriList = stringUrls?.let {
            it.map { strUrl ->
                Uri.parse(strUrl)
            }
        } ?: return Result.failure()

        totalImages = uriList.size

        upload(uriList)
        return Result.success()
    }

    private suspend fun upload(uriList: List<Uri>) {
        val storageRef = Firebase.storage.reference

        withContext(Dispatchers.IO) {
            val deferredUpload = uriList.map { uri ->
                val imageRef =
                    storageRef.child("images/${System.currentTimeMillis()}_${uri.lastPathSegment}")

                async {
                    try {
                        val taskSnapshot = imageRef.putFile(uri).await()
                        setForeground(createForegroundInfo("${uploadCount++} / $totalImages is uploaded"))
                        taskSnapshot.metadata?.reference?.downloadUrl?.toString()
                    } catch (e: Exception) {
                        // TODO
                        null
                    }
                }
            }

            deferredUpload.awaitAll().also {
                delay(4000)
            }
        }
    }

    private fun createForegroundInfo(content: String): ForegroundInfo {
        val intent = WorkManager.getInstance(context)
            .createCancelPendingIntent(id)

        val notification = notificationManager?.let {
            it.buildNotification(content)
                .setOngoing(true)
                .addAction(R.drawable.cancel_upload, "Cancel upload", intent)
                .build()
        } ?: throw IllegalStateException("notification manager not exist!")

        return ForegroundInfo(1, notification)
    }

    companion object {
        const val KEY_URI_ARRAY = "KEY_URI_ARRAY"

        var notificationManager: PhotoNotificationManager? = null
        var monitoring: ProcessMonitoring? = null

        fun setPhotoNotificationManager(manager: PhotoNotificationManager) {
            if (notificationManager == null) notificationManager = manager
        }

        fun setProcessMonitoring(monitor: ProcessMonitoring) {
            if (monitoring == null) monitoring = monitor
        }
    }
}