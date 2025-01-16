package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.cc231008.bookstoreapp.data.db.WishlistEntity
import edu.cc231008.bookstoreapp.ui.AppViewModelProvider
import edu.cc231008.bookstoreapp.ui.BookDetailViewModel


@Composable
fun BookDetailsScreen(
    bookId: String,
    bookDetailViewModel: BookDetailViewModel = viewModel(factory = AppViewModelProvider.DetailFactory),
) {
    val state by bookDetailViewModel.bookDetailUiState.collectAsStateWithLifecycle()
    val book = state.book
    // This composable displays details for a specific book based on the provided bookId
    // Currently it only shows a simple text with the book ID
    Column() {
        Text(
            text = book.title,
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )
        Text(
            text = book.price,
            modifier = androidx.compose.ui.Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = {bookDetailViewModel.addBookToWishlist(
                book = WishlistEntity(
                    isbn13 = book.isbn13,
                    title = book.title,
                    subtitle = book.subtitle,
                    price = book.price,
                    image = book.image,
                    url = book.url
                )
            ) }
        ) {
            Text("Add to Wishlist")
        }
    }
        }

