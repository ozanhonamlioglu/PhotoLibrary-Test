package tech.eightbits.photolibrary.di

import android.app.Application
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.eightbits.photolibrary.repository.PhotoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePhotoRepository(
        workManager: WorkManager
    ) = PhotoRepository(workManager)

   @Singleton
   @Provides
   fun provideWorkManager(
       application: Application
   ) = WorkManager.getInstance(application.applicationContext)

}