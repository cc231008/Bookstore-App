package edu.cc231008.bookstoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import edu.cc231008.bookstoreapp.ui.BookUi
import edu.cc231008.bookstoreapp.ui.theme.BookstoreAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        // This line disables the default system window insets handling by the system.
        // It allows us to manage the system bars (status bar, navigation bar) manually,
        // enabling edge-to-edge layouts for immersive designs.

        enableEdgeToEdge()
        setContent {
            BookstoreAppTheme {
                BookUi()
            }
        }
    }
}