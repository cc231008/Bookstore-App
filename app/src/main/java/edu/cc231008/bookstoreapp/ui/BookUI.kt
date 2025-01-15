package edu.cc231008.bookstoreapp.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.cc231008.bookstoreapp.data.repo.BookTemplate
import edu.cc231008.bookstoreapp.ui.screens.BookDetailsScreen


@Composable
fun BookUi(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "home") {
        // Home Screen
        composable(route = "home") {
            HomeScreen(onBookClick = { bookId -> navController.navigate(route = "details/$bookId")
                })
        }
        /*
        // Wishlist Screen
        composable(route = "wishlist") {
            WishlistScreen(navController = navController, wishlistBooks = wishlistBooks)
        }
        // Cart Screen
        composable(route = "cart") {
            CartScreen(navController = navController)
        }

         */
        // Book Details Screen
        composable(route = "details/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            BookDetailsScreen(bookId = bookId)
        }
        /*
        // Checkout Screen
        composable(route = "checkout") {
            CheckoutScreen(navController = navController)
        }
         */
    }
}

@Composable
fun HomeScreen(onBookClick: (String) -> Unit) {

    val bookViewModel: BookViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val books by bookViewModel.bookUiState.collectAsStateWithLifecycle()

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

        SearchBook()

        Spacer(modifier = Modifier.height(8.dp)) // Adds space below the search bar

        // Displays a list of books
        LazyColumn(
            modifier = Modifier.fillMaxSize(), // Takes up the remaining space
            verticalArrangement = Arrangement.spacedBy(8.dp) // Adds space between each book card
        ) {
            items(books) { book -> // Loops through the list of books
                BookCard(book = book, onClick = { onBookClick(book.isbn13) }) // Displays a book card
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

@Composable
fun SearchBook(
    bookViewModel: BookViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var searchQuery by remember { mutableStateOf("") } // Keeps track of what the user types

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between components
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            }, // Update the state as user types
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface, // Background color
                    MaterialTheme.shapes.small // Rounded corners
                )
                .padding(16.dp), // Inner padding
            textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface) // Text styling
        )

        // Button to display the input
        Button(
            onClick = { bookViewModel.searchBooks(searchQuery) }, // Set the result text when clicked
            modifier = Modifier.align(Alignment.CenterHorizontally) // Center the button
        ) {
            Text("Search")
        }

        /*
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(searchResults) { book ->
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
           }
         */
    }
}

