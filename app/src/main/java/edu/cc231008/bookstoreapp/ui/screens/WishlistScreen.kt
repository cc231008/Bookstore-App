package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import edu.cc231008.bookstoreapp.R
import edu.cc231008.bookstoreapp.data.repo.CartTemplate
import edu.cc231008.bookstoreapp.ui.WishListViewModel
import edu.cc231008.bookstoreapp.data.repo.WishlistTemplate
import edu.cc231008.bookstoreapp.ui.AppViewModelProvider
import edu.cc231008.bookstoreapp.ui.CartViewModel

@Composable
fun WishlistScreen(
    navController: NavHostController,
    wishListViewModel: WishListViewModel = viewModel(factory = AppViewModelProvider.wishListFactory), // ViewModel for managing wishlist data
    cartViewModel: CartViewModel = viewModel(factory = AppViewModelProvider.cartFactory)
) {
    val wishListBooks = wishListViewModel.wishlistBooks.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFFF5F5DC),
        topBar = {
            // Top bar
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
                        text = "Wishlist",
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
            // Content for the wishlist
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
                    items(wishListBooks.value) { book ->
                        WishlistCard(
                            book = book,
                            onRemoveClick = {
                                wishListViewModel.removeFromWishlist(book.isbn13)
                            },
                            onAddToCartClick = {
                                wishListViewModel.removeFromWishlist(book.isbn13)
                                cartViewModel.addToCart(
                                    CartTemplate(
                                    id = 0,
                                    isbn13 = book.isbn13,
                                    title = book.title,
                                    subtitle = book.subtitle,
                                    price = book.price,
                                    image = book.image,
                                    url = book.url
                                    )
                                )
                            },
                            onBookClick = {
                                navController.navigate("details/${book.isbn13}") // Navigation zur BookDetailsScreen
                            }
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun WishlistCard(
    book: WishlistTemplate,
    onRemoveClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onBookClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onBookClick() },
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
                        .padding(end = 8.dp)
                )

                // Title and price
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 12.dp)
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
                            .padding(bottom = 25.dp)
                    )

                    Text(
                        text = book.price,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            // Delete Icon
            IconButton(
                onClick = onRemoveClick,
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

            // Shopping-Cart-Icon
            IconButton(
                onClick = onAddToCartClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24),
                    contentDescription = "Add to Cart",
                    tint = Color.Black
                )
            }
        }
    }
}

