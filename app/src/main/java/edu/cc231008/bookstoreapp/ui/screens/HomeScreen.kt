package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.cc231008.bookstoreapp.data.repo.BookTemplate

@Composable
fun HomeScreen(books: List<BookTemplate>, onBookClick: (BookTemplate) -> Unit) {
    // This is the main layout for the Home Screen
    Column(
        modifier = Modifier
            .fillMaxSize() // Takes up the whole screen
    ) {
        // Header with brown background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF704214))
                .padding(vertical = 24.dp)
        ) {
            Text(
                text = "Bookstore", // Header text
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        var searchQuery by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it }, // Updates the state as the user types
                modifier = Modifier
                    .weight(1f) // Makes this element take up remaining space
                    .padding(end = 8.dp) // Adds padding to the right
                    .background( // Adds a background to the text field
                        MaterialTheme.colorScheme.surface, // Background color from theme
                        MaterialTheme.shapes.small // Rounded corners
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp), // Inner padding inside the text field
                textStyle = TextStyle(fontSize = 16.sp) // Font size for the input text
            )
            Button(onClick = { /* Handle search logic */ }) { // A button for searching
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) // Adds space below the search bar

        // Displays a list of books
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(books) { book ->
                BookCard(book = book, onClick = { onBookClick(book) })
            }
        }
    }
}


@Composable
fun BookCard(book: BookTemplate, onClick: () -> Unit) {
    // This is a single card that displays book details
    Card(
        modifier = Modifier
            .fillMaxWidth() // Takes up the full width
            .padding(horizontal = 8.dp, vertical = 4.dp), // Adds some padding
        onClick = onClick, // Calls the onClick function when the card is clicked
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Adds a shadow
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Background color of the card
    ) {
        Column(modifier = Modifier.padding(16.dp)) { // Adds padding inside the card
            Text(
                text = book.title, // Displays the book's title
                style = MaterialTheme.typography.titleMedium, // Uses the app's medium title style
                modifier = Modifier.padding(bottom = 8.dp) // Adds space below the title
            )
            Text(
                text = "Price: ${book.price}", // Displays the price of the book
                style = MaterialTheme.typography.bodyMedium // Uses the body text style
            )
        }
    }
}
