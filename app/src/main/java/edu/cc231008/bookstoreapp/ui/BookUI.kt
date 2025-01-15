package edu.cc231008.bookstoreapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.cc231008.bookstoreapp.data.repo.BookTemplate
import edu.cc231008.bookstoreapp.ui.navigation.AppNavigation



@Composable
fun BookUi(
    navController: NavHostController = rememberNavController(),
    bookViewModel: BookViewModel = viewModel(factory = AppViewModelProvider.Factory) // Initialize the ViewModel
) {
    val books by bookViewModel.bookUiState.collectAsStateWithLifecycle() // This variable stores the list of books

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

    AppNavigation(
        navController = navController,
        books = books,
        wishlistBooks = wishlistBooks,
        onSearchResult = { query ->
            bookViewModel.searchBooks(query)
        }
        )
}



