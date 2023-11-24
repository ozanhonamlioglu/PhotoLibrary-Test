package tech.eightbits.photolibrary.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.eightbits.photolibrary.presentation.components.AddMorePictureButton
import tech.eightbits.photolibrary.presentation.components.ListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val cloudList by viewModel.urlFlow.collectAsStateWithLifecycle()
    val screenWidthDp = LocalConfiguration.current.screenWidthDp

    val minItemWidth = screenWidthDp / 5

    LaunchedEffect(key1 = true) {
        viewModel.fetchPhotos()
    }

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            Text(
                "Photo Library",
                modifier = Modifier.padding(16.dp),
                fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                fontWeight = FontWeight.Bold
            )
            Divider()
            AddMorePictureButton(onItemsSelected = {
                viewModel.startUploadProcess(it)
            })
        }
    }) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = minItemWidth.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                itemsIndexed(
                    cloudList
                ) {index, url ->
                    ListItem(
                        minItemWidth = minItemWidth,
                        url = url
                    )
                }
            }

        }
    }

}