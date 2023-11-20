package tech.eightbits.photolibrary.repository

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import tech.eightbits.photolibrary.worker.PhotoUploadWorkManager
import java.util.UUID
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val workManager: WorkManager
) {

    private fun saveWorkerId(id: UUID) {
        // TODO
    }

    fun upload(items: List<String>) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<PhotoUploadWorkManager>()
            .setInputData(
                workDataOf(
                    PhotoUploadWorkManager.KEY_URI_ARRAY to items
                )
            )
            .setConstraints(constraints)
            .build()

        saveWorkerId(request.id)
        workManager.enqueue(request)
    }

}