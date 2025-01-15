package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.cc231008.bookstoreapp.data.repo.BookTemplate

@Composable
fun WishlistScreen(
    navController: NavHostController, // Controller to handle navigation between screens
    wishlistBooks: List<BookTemplate> // List of books in the wishlist
) {
    // Display a vertically scrollable list of wishlist books
    LazyColumn(
        modifier = Modifier.fillMaxSize(), // Fills the entire screen
        verticalArrangement = Arrangement.spacedBy(8.dp) // Adds spacing between items
    ) {
        // Loop through each book in the wishlist and display it as a card
        items(wishlistBooks) { book ->
            BookCard(
                book = book, // Pass the current book to the card
                onClick = {
                    // Navigate to the book details screen when the card is clicked
                    navController.navigate(route = "details/${book.isbn13}")
                }
            )
        }
    }
}


