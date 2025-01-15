package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

//
@Composable
fun BookDetailsScreen(bookId: String) {
    // This composable displays details for a specific book based on the provided bookId
    // Currently it only shows a simple text with the book ID
    androidx.compose.material3.Text(text = "Details for Book ID: $bookId")
}
