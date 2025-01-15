package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.cc231008.bookstoreapp.data.repo.BookTemplate

@Composable
fun HomeScreen(
    books: List<BookTemplate>,
    onBookClick: (BookTemplate) -> Unit,
    onSearchClick: (String) -> Unit
) {

    // This Column is basically the layout for the Home Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Displays the title of the app
        Text(
            text = "Bookstore",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Function for searching books
        SearchBook(onSearchClick)

        Spacer(modifier = Modifier.height(8.dp))

        // Displays a list of books
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Loops through the list of books
            items(books) { book ->
                // A book card
                BookCard(book = book, onClick = { onBookClick(book)})
            }
        }
    }
}

@Composable
fun BookCard(book: BookTemplate, onClick: () -> Unit) {
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

@Composable
fun SearchBook(
    onSearchClick: (String) -> Unit
) {
    // Variable for storing the user input for searching
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface,
                    MaterialTheme.shapes.small
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small
                )
                .padding(8.dp)
        ) {
            // Basic text field for user input, where the user types what they want to search
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it }, // Update the state as user types
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Search...",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )
                    }
                    innerTextField() // Actual text input
                }
            )

        }

        // Button to display the input
        Button(
            // Set the result text when clicked. This search query passes through lambdas to reach the ViewModel function (searchBooks())
            onClick = { onSearchClick(searchQuery) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Search")
        }
    }
}
