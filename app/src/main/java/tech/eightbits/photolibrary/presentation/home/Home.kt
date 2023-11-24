package tech.eightbits.photolibrary.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.eightbits.photolibrary.presentation.components.AddMorePictureButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val cloudList by viewModel.monitoring.urlFlow.collectAsStateWithLifecycle()

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            Text(
                "Photo Library",
                modifier = Modifier.padding(16.dp),
                fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                fontWeight = FontWeight.Bold
            )
            Divider()
            AddMorePictureButton(onItemsSelected = {})
        }
    }) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                items(cloudList.size) {

                }
            }

        }
    }

}