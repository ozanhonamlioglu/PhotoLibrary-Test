package tech.eightbits.photolibrary.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tech.eightbits.photolibrary.presentation.components.AddMorePictureButton

@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel()
) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(128.dp),
            modifier = Modifier.fillMaxSize().padding(5.dp)
        ) {
            items(1) {
                AddMorePictureButton(
                    onItemsSelected = viewModel::startUploadProcess
                )
            }
        }

    }

}