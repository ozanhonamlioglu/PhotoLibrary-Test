package tech.eightbits.photolibrary.repository

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.scopes.ActivityRetainedScoped
import tech.eightbits.photolibrary.worker.PhotoNotificationManager
import tech.eightbits.photolibrary.worker.PhotoUploadWorkManager
import javax.inject.Inject

@ActivityRetainedScoped
class PhotoRepository @Inject constructor(
    private val workManager: WorkManager,
    private val photoNotificationManager: PhotoNotificationManager
) {
    init {
        PhotoUploadWorkManager.setPhotoNotificationManager(photoNotificationManager)
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
}