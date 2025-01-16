package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.cc231008.bookstoreapp.data.WishListViewModel
import edu.cc231008.bookstoreapp.data.repo.WishlistTemplate
import edu.cc231008.bookstoreapp.ui.AppViewModelProvider

@Composable
fun WishlistScreen(
    navController: NavHostController, // Controller to handle navigation between screens
    wishListViewModel: WishListViewModel = viewModel(factory = AppViewModelProvider.wishListFactory) // ViewModel for managing wishlist data
) {

    val wishListBooks = wishListViewModel.wishlistBooks.collectAsStateWithLifecycle()

    // Display a vertically scrollable list of wishlist books
    LazyColumn(
        modifier = Modifier.fillMaxSize(), // Fills the entire screen
        verticalArrangement = Arrangement.spacedBy(8.dp) // Adds spacing between items
    ) {
        // Loop through each book in the wishlist and display it as a card
        items(wishListBooks.value) { book ->
            BookCardWishList(
                book = book, // Pass the current book to the card
                onClick = {
                    // Navigate to the book details screen when the card is clicked
                    navController.navigate(route = "details/${book.isbn13}")
                }
            )
        }
    }
    }

@Composable
fun BookCardWishList(book: WishlistTemplate, onClick: () -> Unit) {
    // This is a single card that displays book details
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = book.title, // The book's title
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Price: ${book.price}", // The price of the book
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


