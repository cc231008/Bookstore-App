package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import edu.cc231008.bookstoreapp.data.repo.BookTemplate
import kotlinx.coroutines.delay
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    books: List<BookTemplate>, // List of books to display
    onSearchChange: (String) -> Unit, // Callback for search query changes
    onBookClick: (BookTemplate) -> Unit // Callback when a book is clicked
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = Color(0xFF745447)
    val pageBackgroundColor = Color(0xFFF5F5DC)

    // Change the system status bar color
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = false
        )
    }

    // Custom Saver to persist TextFieldValue state across configuration changes
    val textFieldValueSaver = Saver<TextFieldValue, String>(
        save = { it.text }, // Save only the text content
        restore = { TextFieldValue(it) } // Restore TextFieldValue from the text
    )

    var searchQuery by rememberSaveable(stateSaver = textFieldValueSaver) { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        containerColor = pageBackgroundColor, // Set the background color of the scaffold
        topBar = {
            // Top bar with a title and a search bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(statusBarColor)
            ) {
                // Column for the "Bookstore" title
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bookstore",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
                        color = Color.White
                    )
                }

                // Search bar with a shadow
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .offset(y = 25.dp)
                        .shadow(
                            elevation = 12.dp, // Add shadow for a floating effect
                            shape = RoundedCornerShape(25.dp),
                            clip = false
                        )
                        .background(Color(0xFFFAF3DD), RoundedCornerShape(25.dp)) // Light background for search bar
                        .padding(horizontal = 16.dp, vertical = 6.dp) // Inner padding for content
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Back button to clear search
                        IconButton(
                            onClick = {
                                searchQuery = TextFieldValue("") // Clear the search field
                                onSearchChange("") // Show the full list
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back Button",
                                tint = if (searchQuery.text.isNotBlank()) Color.Black else Color.Gray // Active/inactive tint
                            )
                        }

                        // Text field for search
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search for books...") }, // Placeholder text
                            singleLine = true, // Restrict to one line
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Search // Set the keyboard IME action to search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    if (searchQuery.text.isNotBlank()) {
                                        onSearchChange(searchQuery.text) // Trigger search callback
                                    }
                                }
                            ),
                            modifier = Modifier.weight(1f), // Take up available width
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Search icon button
                        IconButton(
                            onClick = {
                                if (searchQuery.text.isNotBlank()) {
                                    onSearchChange(searchQuery.text) // Trigger search callback
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
        },
        content = { innerPadding ->
            // Main content area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Spacer(modifier = Modifier.height(40.dp)) // Add space below the search bar

                LazyColumn(
                    modifier = Modifier.fillMaxSize(), // Fill available space
                    verticalArrangement = Arrangement.spacedBy(8.dp), // Add spacing between items
                    contentPadding = PaddingValues(16.dp) // Padding around the list
                ) {
                    items(books) { book ->
                        BookCard(
                            book = book, // Display each book in the list
                            onClick = { onBookClick(book) } // Handle book click
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun BookCard(book: BookTemplate, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD7C5A1))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book Cover Image
            Image(
                painter = rememberAsyncImagePainter(model = book.image),
                contentDescription = "${book.title} Cover",
                modifier = Modifier
                    .width(160.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(end = 8.dp)
            )

            // Title and Price
            Column {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp)
                )
                Text(
                    text = "Price: ${book.price}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}


@Composable
fun AnimatedHomeScreen(
    books: List<BookTemplate>, // List of books to display
    onSearchChange: (String) -> Unit, // Callback for search query changes
    onBookClick: (BookTemplate) -> Unit // Callback when a book is clicked
) {
    // State to control the visibility of the HomeScreen
    var showHomeScreen by remember { mutableStateOf(false) }

    // Add delay for a smooth animation effect
    LaunchedEffect(Unit) {
        delay(300)
        showHomeScreen = true
    }

    // Animated visibility for the HomeScreen
    AnimatedVisibility(
        visible = showHomeScreen,
        enter = fadeIn(animationSpec = tween(1000)), // Fade in over 1 second
        exit = fadeOut(animationSpec = tween(500)) // Fade out over 0.5 seconds
    ) {
        HomeScreen(
            books = books,
            onSearchChange = onSearchChange,
            onBookClick = onBookClick
        )
    }
}
