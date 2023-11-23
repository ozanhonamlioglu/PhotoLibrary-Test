package tech.eightbits.photolibrary.worker

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProcessMonitoring {

    private val mUrlFlow = MutableStateFlow<List<String>>(emptyList())
    val urlFlow = mUrlFlow.asStateFlow()

    fun updateUrlFlow(urls: List<String>) {
        mUrlFlow.update { urls }
    }

    fun addSingleItem(url: String) {
        mUrlFlow.update {
            it + url
        }
    }

}