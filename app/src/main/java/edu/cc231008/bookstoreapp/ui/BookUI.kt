package edu.cc231008.bookstoreapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.cc231008.bookstoreapp.ui.navigation.AppNavigation



@Composable
fun BookUi(
    navController: NavHostController = rememberNavController(),
    bookViewModel: BookViewModel = viewModel(factory = AppViewModelProvider.Factory), // Initialize the ViewModel
) {
    val books by bookViewModel.bookUiState.collectAsStateWithLifecycle() // This variable stores the list of books
    //val book by bookDetailViewModel.bookDetailUiState.collectAsStateWithLifecycle(

    AppNavigation(
        navController = navController,
        books = books,
        onSearchResult = { query ->
            bookViewModel.searchBooks(query)
        },
        onResetClick = {
            bookViewModel.resetSearch()
        }
    )
}



