package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC)) // Light beige background
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFF704214)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Bookstore",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Shopping Cart List Title
        Text(
            text = "Your Cart Items:",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Shopping Cart List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Adds spacing between items
        ) {
            items(cartItems) { cartItem ->
                ShoppingCartCard(
                    book = cartItem,
                    onClick = {
                        onBookClick(cartItem) // Handles clicking on a cart item
                    }
                )
            }
        }
    }
}

@Composable
fun ShoppingCartCard(
    book: CartTemplate,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD7C5A1))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Book Title
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Book Price
            Text(
                text = "Price: ${book.price}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Buttons Row (Placeholder for Purchase and Remove)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Purchase Button
                Button(
                    onClick = { /* Add Purchase Logic */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4E6C1)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Purchase", color = Color.Black)
                }

                // Remove from Cart Button
                Button(
                    onClick = { /* Add Remove Logic */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB87333)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Remove from Cart", color = Color.White)
                }
            }
        }
    }
}
