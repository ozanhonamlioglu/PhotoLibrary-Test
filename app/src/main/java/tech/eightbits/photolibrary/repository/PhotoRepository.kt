package tech.eightbits.photolibrary.repository

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.scopes.ActivityRetainedScoped
import tech.eightbits.photolibrary.domain.models.ListItemModel
import tech.eightbits.photolibrary.worker.PhotoNotificationManager
import tech.eightbits.photolibrary.worker.PhotoUploadWorkManager
import tech.eightbits.photolibrary.worker.ProcessMonitoring
import javax.inject.Inject

@ActivityRetainedScoped
class PhotoRepository @Inject constructor(
    private val workManager: WorkManager,
    private val photoNotificationManager: PhotoNotificationManager,
    private val monitoring: ProcessMonitoring
) {
    init {
        PhotoUploadWorkManager.setPhotoNotificationManager(photoNotificationManager)
        PhotoUploadWorkManager.setProcessMonitoring(monitoring)
    }

    fun upload(items: List<String>) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<PhotoUploadWorkManager>()
            .setInputData(
                workDataOf(
                    PhotoUploadWorkManager.KEY_URI_ARRAY to items.toTypedArray()
                )
            )
            .setConstraints(constraints)
            .build()

        workManager.enqueue(request)
    }

    fun loadInitialItems() {
        Firebase.firestore.collection("images")
            .get()
            .addOnSuccessListener {result ->
                val updateList = mutableListOf<ListItemModel>()

                for (document in result) {
                    val doc = document.data.get("url") as String?
                    if(doc != null) updateList.add(ListItemModel(url = doc, fromCloud = true))
                }

                monitoring.setInitial(updateList)
            }
    }
}