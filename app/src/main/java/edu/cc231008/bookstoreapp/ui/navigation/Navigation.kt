package edu.cc231008.bookstoreapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    var currentRoute by remember { mutableStateOf("home") }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            currentRoute = backStackEntry.destination.route ?: "home"
            android.util.Log.d("AppNavigation", "Current Route Updated: $currentRoute")
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onNavItemClick = { route ->
                    if (route != currentRoute) {
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable(route = "home") {
                HomeScreen(
                    books = books,
                    onBookClick = { book ->
                        navController.navigate(route = "details/${book.isbn13}")
                    },
                    onSearchClick = { query ->
                        onSearchResult(query)
                    }
                )
            }
            composable(route = "wishlist") {
                WishlistScreen(navController = navController, wishlistBooks = wishlistBooks)
            }
            composable(route = "cart") {
                CartScreen(navController = navController)
            }
            composable(route = "details/{bookId}") { backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                BookDetailsScreen(bookId = bookId)
            }
            composable(route = "checkout") {
                CheckoutScreen(navController = navController)
            }
        }
    }
}
