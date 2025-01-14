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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.cc231008.bookstoreapp.data.repo.BookTemplate

@Composable
fun HomeScreen(books: List<BookTemplate>, onBookClick: (BookTemplate) -> Unit) {
    // This is the main layout for the Home Screen
    Column(
        modifier = Modifier
            .fillMaxSize() // Takes up the whole screen
            .padding(16.dp) // Adds padding to all sides
    ) {
        // Displays the title of the app
        Text(
            text = "Bookstore", // The header text
            style = MaterialTheme.typography.titleLarge, // Uses the app's large title style
            modifier = Modifier.padding(vertical = 8.dp) // Adds padding above and below the text
        )

        Spacer(modifier = Modifier.height(8.dp)) // Adds some space below the header

        // Search Bar
        var searchQuery by remember { mutableStateOf("") } // Keeps track of what the user types
        Row(
            modifier = Modifier
                .fillMaxWidth() // Makes the search bar take the full width
                .padding(vertical = 8.dp), // Adds some padding vertically
            horizontalArrangement = Arrangement.SpaceBetween // Ensures space between elements
        ) {
            BasicTextField(
                value = searchQuery, // Binds the text field to the state variable
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
            modifier = Modifier.fillMaxSize(), // Takes up the remaining space
            verticalArrangement = Arrangement.spacedBy(8.dp) // Adds space between each book card
        ) {
            items(books) { book -> // Loops through the list of books
                BookCard(book = book, onClick = { onBookClick(book) }) // Displays a book card
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
