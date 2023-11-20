package tech.eightbits.photolibrary.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class PhotoUploadWorkManager(
    private val context: Context,
    private val parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }

    companion object {
        const val  KEY_URI_ARRAY = "KEY_URI_ARRAY"
    }
}