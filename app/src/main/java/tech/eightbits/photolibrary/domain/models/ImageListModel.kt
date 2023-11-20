package tech.eightbits.photolibrary.domain.models

import android.net.Uri

data class ImageListModel(
    val button: Boolean,
    val local: Boolean,
    val url: String,
    val uri: Uri
)
