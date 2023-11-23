package tech.eightbits.photolibrary

import android.app.Application
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import tech.eightbits.photolibrary.data.PhotoNotificationManager
import javax.inject.Inject

@HiltAndroidApp
class PhotoApp : Application() {

    @Inject
    lateinit var photoNotificationManager: PhotoNotificationManager

    override fun onCreate() {
        super.onCreate()
        photoNotificationManager.buildNotificationChannel()
    }

}