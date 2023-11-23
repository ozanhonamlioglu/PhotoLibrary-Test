package tech.eightbits.photolibrary.presentation.home

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.eightbits.photolibrary.repository.PhotoRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PhotoRepository
) : ViewModel() {

    fun startUploadProcess(items: List<Uri>) {
        repository.upload(items.map { it.toString() })
    }

    fun fetchPhotos() {
        // TODO
    }

    companion object {
        val storagePermission =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES
            else Manifest.permission.READ_EXTERNAL_STORAGE
        val notificationPermission = Manifest.permission.POST_NOTIFICATIONS
    }
}