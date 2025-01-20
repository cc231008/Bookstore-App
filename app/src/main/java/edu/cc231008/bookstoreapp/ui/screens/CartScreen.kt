package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import edu.cc231008.bookstoreapp.data.repo.CartTemplate
import edu.cc231008.bookstoreapp.ui.AppViewModelProvider
import edu.cc231008.bookstoreapp.ui.CartViewModel

@Composable
fun CartScreen(
    onBookClick: (CartTemplate) -> Unit,
    onPurchase: (CartTemplate) -> Unit,
    cartViewModel: CartViewModel = viewModel(factory = AppViewModelProvider.cartFactory)
) {
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFFF5F5DC),
        topBar = {
            // Top-Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(Color(0xFF745447))
            ) {
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

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .offset(y = 25.dp)
                        .background(Color(0xFFFAF3DD), RoundedCornerShape(25.dp))
                        .padding(horizontal = 16.dp, vertical = 18.dp)
                ) {
                    Text(
                        text = "Your Shopping Cart",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 25.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        },
        content = { innerPadding ->
            // Content of shopping cart
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(cartItems) { cartItem ->
                        ShoppingCartCard(
                            book = cartItem,
                            onClick = { onBookClick(cartItem) },
                            onPurchase = { onPurchase(cartItem) },
                            onRemove = { cartViewModel.removeFromCart(cartItem.id) }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ShoppingCartCard(
    book: CartTemplate,
    onClick: () -> Unit,
    onPurchase: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD7C5A1))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                )

                // Title and Price
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
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
                            .padding(top = 16.dp, bottom = 30.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = book.price,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.padding(end = 12.dp)
                        )

                        // "Purchase"-Button
                        Button(
                            onClick = onPurchase,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB87333)),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(start = 4.dp, end = 4.dp),
                            modifier = Modifier
                                .height(40.dp)
                                .width(100.dp)
                        ) {
                            Text(
                                text = "Purchase",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 18.sp,
                                    color = Color.White
                                ),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }

            // Delete Icon
            IconButton(
                onClick = onRemove,
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.BottomStart)
                    .padding(top = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from Cart",
                    tint = Color.Black
                )
            }
        }
    }
}


