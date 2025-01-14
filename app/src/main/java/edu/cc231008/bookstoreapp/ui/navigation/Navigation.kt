package edu.cc231008.bookstoreapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.cc231008.bookstoreapp.data.repo.BookTemplate
import edu.cc231008.bookstoreapp.ui.screens.*

@Composable
fun AppNavigation(
    navController: NavHostController,
    books: List<BookTemplate>,
    wishlistBooks: List<BookTemplate>
) {
    NavHost(navController = navController, startDestination = "home") {
        // Home Screen
        composable(route = "home") {
            HomeScreen(books = books, onBookClick = { book ->
                navController.navigate(route = "details/${book.isbn13}")
            })
        }
        // Wishlist Screen
        composable(route = "wishlist") {
            WishlistScreen(navController = navController, wishlistBooks = wishlistBooks)
        }
        // Cart Screen
        composable(route = "cart") {
            CartScreen(navController = navController)
        }
        // Book Details Screen
        composable(route = "details/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            BookDetailsScreen(navController = navController, bookId = bookId)
        }
        // Checkout Screen
        composable(route = "checkout") {
            CheckoutScreen(navController = navController)
        }
    }
}

