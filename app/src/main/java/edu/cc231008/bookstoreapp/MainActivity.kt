package edu.cc231008.bookstoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import edu.cc231008.bookstoreapp.ui.BookUi
import edu.cc231008.bookstoreapp.ui.theme.BookstoreAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookstoreAppTheme {
                BookUi()
            }
        }
    }
}