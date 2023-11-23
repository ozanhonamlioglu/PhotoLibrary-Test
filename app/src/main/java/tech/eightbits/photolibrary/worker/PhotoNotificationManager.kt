package tech.eightbits.photolibrary.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import tech.eightbits.photolibrary.R
import javax.inject.Inject

class PhotoNotificationManager @Inject constructor(
    private val notificationManager: NotificationManager,
    private val context: Context
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun buildNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(channel)
    }

    fun buildNotification(content: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.cloud_upload)
            .setContentTitle("Files are uploading in background")
            .setContentText(content)
    }

    fun cancelNotification() {
        val notification = NotificationManagerCompat.from(context)
        // TODO notification.cancel(1)
    }

    companion object {
        const val CHANNEL_ID = "photo_upload_foreground_service"
        const val CHANNEL_NAME = "Upload tracing"
    }
}