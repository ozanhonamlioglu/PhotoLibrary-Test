package tech.eightbits.photolibrary.presentation.home

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import tech.eightbits.photolibrary.repository.PhotoRepository
import tech.eightbits.photolibrary.worker.ProcessMonitoring
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PhotoRepository,
    private val monitoring: ProcessMonitoring
) : ViewModel() {

    // TODO for now we just list items which has "url" value
    val urlFlow = monitoring.dataFlow.map {list ->
        list.mapNotNull { it.url }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3000),
        emptyList()
    )

    fun startUploadProcess(items: List<Uri>) {
        repository.upload(items.map { it.toString() })
    }

    fun fetchPhotos() = repository.loadInitialItems()

    companion object {
        val storagePermission =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES
            else Manifest.permission.READ_EXTERNAL_STORAGE
        val notificationPermission = Manifest.permission.POST_NOTIFICATIONS
    }
}