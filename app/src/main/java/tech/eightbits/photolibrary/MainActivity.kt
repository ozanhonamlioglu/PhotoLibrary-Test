package tech.eightbits.photolibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import tech.eightbits.photolibrary.presentation.home.Home
import tech.eightbits.photolibrary.ui.theme.PhotoLibraryTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoLibraryTheme {
                Home()
            }
        }
    }
}