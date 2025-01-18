package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.cc231008.bookstoreapp.data.repo.CartTemplate
import edu.cc231008.bookstoreapp.ui.AppViewModelProvider
import edu.cc231008.bookstoreapp.ui.CartViewModel

@Composable
fun CartScreen(
    onBookClick: (CartTemplate) -> Unit,
    cartViewModel: CartViewModel = viewModel(factory = AppViewModelProvider.cartFactory)
) {
   val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()

    LazyColumn {
    items(cartItems) { cartItem ->
        ShoppingCartCard(
            book = cartItem,
            onClick = {
                onBookClick(cartItem)
            }
        )
    }

    }
}

@Composable
fun ShoppingCartCard(
    book: CartTemplate,
    onClick: () -> Unit) {
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