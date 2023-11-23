package tech.eightbits.photolibrary.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import tech.eightbits.photolibrary.presentation.home.HomeViewModel
import tech.eightbits.photolibrary.utils.PermissionState

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

    Button(
        modifier = Modifier.padding(0.dp),
        shape = RectangleShape,
        onClick = {
            permissionLauncher.launch(
                listOf(
                    HomeViewModel.storagePermission,
                    HomeViewModel.notificationPermission
                ).toTypedArray()
            )
        },
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Card(
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Text(
                text = "Add More Picture",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp),
                lineHeight = 1.5.em
            )
        }
    }

}