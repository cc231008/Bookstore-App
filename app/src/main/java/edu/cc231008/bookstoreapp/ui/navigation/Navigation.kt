package edu.cc231008.bookstoreapp.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.cc231008.bookstoreapp.data.repo.BookTemplate
import edu.cc231008.bookstoreapp.ui.screens.*

@Composable
fun AppNavigation(
    navController: NavHostController,
    books: List<BookTemplate>,
    wishlistBooks: List<BookTemplate>,
    onSearchResult: (String) -> Unit
) {
    // Tracks the current route in the navigation system
    var currentRoute by remember { mutableStateOf("home") }

    // Controls the visibility of content and bottom navigation bar
    var showContent by remember { mutableStateOf(false) }

    // Synchronize the visibility state and track the current route
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            currentRoute = backStackEntry.destination.route ?: "home" // Default to "home" if route is null
            android.util.Log.d("AppNavigation", "Current Route Updated: $currentRoute")
            showContent = currentRoute != "welcome" // Hide navbar on the Welcome screen
        }
    }

    Scaffold(
        bottomBar = {
            // Animate the visibility of the bottom navigation bar
            AnimatedVisibility(
                visible = showContent, // Only visible when `showContent` is true
                enter = fadeIn(animationSpec = tween(1000)), // Smooth fade-in animation
                exit = fadeOut(animationSpec = tween(500))   // Smooth fade-out animation
            ) {
                // Display the BottomNavBar if the current route is not "welcome"
                if (currentRoute != "welcome") {
                    BottomNavBar(
                        currentRoute = currentRoute, // Pass the current route for highlighting
                        onNavItemClick = { route -> // Handle navigation item clicks
                            if (route != currentRoute) {
                                navController.navigate(route) {
                                    launchSingleTop = true // Avoid creating duplicate destinations
                                    restoreState = true   // Restore the state of previous destinations
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Define the navigation graph
        NavHost(
            navController = navController,
            startDestination = "welcome", // Start with the Welcome screen
            modifier = Modifier
                .padding(innerPadding) // Adjust padding for the scaffold content
                .fillMaxSize()         // Make the navigation container fill the screen
        ) {
            // Define the "welcome" screen destination
            composable(route = "welcome") {
                WelcomeScreen(navController = navController) // Pass the navController to navigate away
            }
            // Define the "home" screen destination
            composable(route = "home") {
                AnimatedHomeScreen(
                    books = books, // Pass the list of books
                    onBookClick = { book -> // Handle book clicks
                        navController.navigate(route = "details/${book.isbn13}") // Navigate to details screen
                    },
                    onSearchClick = { query -> // Handle search action
                        onSearchResult(query) // Pass the query to the search handler
                    }
                )
            }
            // Define the "wishlist" screen destination
            composable(route = "wishlist") {
                WishlistScreen(
                    navController = navController,
                    wishlistBooks = wishlistBooks // Pass the wishlist books to display
                )
            }
            // Define the "cart" screen destination
            composable(route = "cart") {
                CartScreen(navController = navController) // Pass the navController for navigation
            }
            // Define the "details" screen destination with a dynamic bookId parameter
            composable(route = "details/{bookId}") { backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId") ?: "" // Retrieve bookId from arguments
                BookDetailsScreen(bookId = bookId) // Pass the bookId to display book details
            }
            // Define the "checkout" screen destination
            composable(route = "checkout") {
                CheckoutScreen(navController = navController) // Pass the navController for navigation
            }
        }
    }
}
