package tech.eightbits.photolibrary.worker

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import tech.eightbits.photolibrary.domain.models.ListItemModel

class ProcessMonitoring {

    private val mDataFlow = MutableStateFlow<List<ListItemModel>>(emptyList())
    val dataFlow = mDataFlow.asStateFlow()

    fun setInitial(items: List<ListItemModel>) {
        mDataFlow.update { items }
    }

    fun addSingleItem(item: ListItemModel) {
        mDataFlow.update {
            it + item
        }
    }

}