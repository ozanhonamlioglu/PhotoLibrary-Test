package tech.eightbits.photolibrary.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import tech.eightbits.photolibrary.R
import tech.eightbits.photolibrary.presentation.home.HomeViewModel
import tech.eightbits.photolibrary.utils.PermissionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMorePictureButton(
    onItemsSelected: (List<Uri>) -> Unit
) {
    val permissionGranted = remember {
        mutableStateOf(PermissionState.INITIALIZING)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
    ) {
        onItemsSelected(it)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {result ->
        if (result.all { it.value }) {
            permissionGranted.value = PermissionState.GRANTED
            photoPickerLauncher.launch("image/*")
        } else {
            permissionGranted.value = PermissionState.DENIED
        }
    }

    NavigationDrawerItem(
        icon = {
            Image(
                painter = painterResource(id = R.drawable.cloud_upload),
                contentDescription = "upload"
            )
        },
        label = { Text(text = "Upload images") },
        selected = false,
        onClick = {
            permissionLauncher.launch(
                listOf(
                    HomeViewModel.storagePermission,
                    HomeViewModel.notificationPermission
                ).toTypedArray()
            )
        }
    )

}