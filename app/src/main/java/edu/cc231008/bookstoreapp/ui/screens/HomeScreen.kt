package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.cc231008.bookstoreapp.data.repo.BookTemplate
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    books: List<BookTemplate>,
    onBookClick: (BookTemplate) -> Unit,
    onResetClick: () -> Unit,
    onSearchClick: (String) -> Unit
) {
    // The main layout for the Home Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC)) // Beige background color for the Home Screen
    ) {

        // Header containing the app's title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp) // Fixed height for the header
                .background(Color(0xFF704214)) // Brown background for the header
        ) {
            Text(
                text = "Bookstore", // App title
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold, // Bold styling
                    fontSize = 28.sp, // Font size
                    color = Color.White // White text color
                ),
                modifier = Modifier.align(Alignment.Center) // Centers the text within the box
            )
        }

        // Section containing the search bar and the list of books
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp) // Padding for better alignment and spacing
        ) {
            SearchBook(
                onResetClick = onResetClick,
                onSearchClick = onSearchClick
            ) // Search bar component

            Spacer(modifier = Modifier.height(16.dp)) // Spacing between the search bar and the book list


            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Space between book items
            ) {
                // Display each book using a BookCard
                items(books) { book ->
                    BookCard(book = book, onClick = { onBookClick(book) })
                }
            }
        }
    }
}

@Composable
fun BookCard(book: BookTemplate, onClick: () -> Unit) {
    // A card displaying details of a single book
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp), // Card padding
        onClick = onClick, // Action triggered on card click
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Card shadow
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD7C5A1) // Light beige background for the card
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Book title
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp) // Spacing below the title
            )
            // Book price
            Text(
                text = "Price: ${book.price}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun SearchBook(
    onResetClick: () -> Unit,
    onSearchClick: (String) -> Unit) {
    // State to hold the current query in the search bar
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Padding for spacing around the search bar
        verticalArrangement = Arrangement.spacedBy(8.dp) // Space between components
    ) {
        // Search input field
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface, // Surface background color
                    MaterialTheme.shapes.small // Rounded corners
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary, // Border color
                    shape = MaterialTheme.shapes.small // Border shape matches background
                )
                .padding(8.dp) // Padding inside the box
        ) {
            BasicTextField(
                value = searchQuery, // Current search query
                onValueChange = { searchQuery = it }, // Updates the query as the user types
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), // Padding inside the text field
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface // Text color
                ),
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        // Placeholder text when the search query is empty
                        Text(
                            text = "Search...",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                    }
                    innerTextField() // Displays the entered text
                }
            )
        }

        // Search button
        Button(
            onClick = { onSearchClick(searchQuery) }, // Trigger the search action
            modifier = Modifier.align(Alignment.CenterHorizontally) // Center-align the button
        ) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onResetClick() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Reset")
        }
    }
}

@Composable
fun AnimatedHomeScreen(
    books: List<BookTemplate>,
    onBookClick: (BookTemplate) -> Unit,
    onSearchClick: (String) -> Unit,
    onResetClick: () -> Unit
) {
    // State to control the visibility of the HomeScreen
    var showHomeScreen by remember { mutableStateOf(false) }

    // Delays the visibility to create a smooth transition
    LaunchedEffect(Unit) {
        delay(300) // Slight delay before showing the HomeScreen
        showHomeScreen = true
    }

    // Animates the HomeScreen appearance
    AnimatedVisibility(
        visible = showHomeScreen,
        enter = fadeIn(animationSpec = tween(1000)), // Fade-in animation over 1 second
        exit = fadeOut(animationSpec = tween(500))  // Fade-out animation over 0.5 seconds
    ) {
        HomeScreen(
            books = books,
            onBookClick = onBookClick,
            onSearchClick = onSearchClick,
            onResetClick = onResetClick
        )
    }
}
