package tech.eightbits.photolibrary.worker

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PhotoUploadWorkManager(
    private val context: Context,
    private val parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        val stringUrls = parameters.inputData.getStringArray(KEY_URI_ARRAY)
        val uriList = stringUrls?.let {
            it.map { strUrl ->
                Uri.parse(strUrl)
            }
        } ?: return Result.failure()

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
                        taskSnapshot.metadata?.reference?.downloadUrl?.toString()
                    } catch (e: Exception) {
                        // TODO
                        null
                    }
                }
            }

            deferredUpload.awaitAll()
        }


    }

    companion object {
        const val KEY_URI_ARRAY = "KEY_URI_ARRAY"
    }
}