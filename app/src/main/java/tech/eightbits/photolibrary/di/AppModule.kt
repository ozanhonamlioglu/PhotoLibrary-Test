package tech.eightbits.photolibrary.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.eightbits.photolibrary.worker.PhotoNotificationManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

   @Singleton
   @Provides
   fun provideWorkManager(
       application: Application
   ) = WorkManager.getInstance(application.applicationContext)

    @Singleton
    @Provides
    fun provideNotificationManager(
        application: Application
    ): NotificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @Singleton
    @Provides
    fun providePhotoNotificationManager(
        notificationManager: NotificationManager,
        @ApplicationContext context: Context
    ) = PhotoNotificationManager(notificationManager, context)

}