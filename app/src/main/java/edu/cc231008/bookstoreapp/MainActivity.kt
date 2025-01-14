package edu.cc231008.bookstoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.cc231008.bookstoreapp.data.repo.BookTemplate
import edu.cc231008.bookstoreapp.ui.AppViewModelProvider
import edu.cc231008.bookstoreapp.ui.navigation.AppNavigation
import edu.cc231008.bookstoreapp.ui.theme.BookstoreAppTheme
import edu.cc231008.bookstoreapp.ui.BookViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookstoreAppTheme {
                val bookViewModel: BookViewModel = viewModel(factory = AppViewModelProvider.Factory)
                val books = bookViewModel.bookUiState.collectAsState(initial = emptyList()).value
                val wishlistBooks = listOf(
                    BookTemplate(
                        title = "Wishlist Book 1",
                        subtitle = "Subtitle 1",
                        isbn13 = "123456789",
                        price = "$20",
                        image = "https://example.com/image.jpg",
                        url = "https://example.com"
                    ),
                    BookTemplate(
                        title = "Wishlist Book 2",
                        subtitle = "Subtitle 2",
                        isbn13 = "987654321",
                        price = "$25",
                        image = "https://example.com/image2.jpg",
                        url = "https://example.com"
                    )
                )
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    books = books,
                    wishlistBooks = wishlistBooks
                )
            }
        }
    }
}