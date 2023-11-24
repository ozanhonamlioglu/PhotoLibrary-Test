package tech.eightbits.photolibrary.domain.models

import android.net.Uri

data class ListItemModel(
    val uri: Uri? = null,
    val url: String?,
    val fromCloud: Boolean
)
