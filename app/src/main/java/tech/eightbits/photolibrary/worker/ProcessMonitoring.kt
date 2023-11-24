package tech.eightbits.photolibrary.worker

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import tech.eightbits.photolibrary.domain.models.ListItemModel

class ProcessMonitoring {

    private val mUrlFlow = MutableStateFlow<List<ListItemModel>>(emptyList())
    val urlFlow = mUrlFlow.asStateFlow()

    fun updateUrlFlow(urls: List<ListItemModel>) {
        mUrlFlow.update { urls }
    }

    fun addSingleItem(url: ListItemModel) {
        mUrlFlow.update {
            it + url
        }
    }

}