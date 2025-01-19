package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import edu.cc231008.bookstoreapp.ui.AppViewModelProvider
import edu.cc231008.bookstoreapp.ui.CartViewModel

@Composable
fun CheckoutScreen(
    cartItemId: Int,
    navController: NavHostController,
    cartViewModel: CartViewModel = viewModel(factory = AppViewModelProvider.cartFactory)
) {
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()
    val cartItem = cartItems.find { it.id == cartItemId }
    var showDialog by remember { mutableStateOf(false) }

    if (cartItem != null) {
        Scaffold(
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color(0xFFD7C5A1)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${cartItem.price}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        Button(
                            onClick = { showDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4E6C1)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Purchase", color = Color.Black)
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5DC))
            ) {
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
                            color = Color.White
                        )
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(cartItem.image),
                        contentDescription = "Book Cover",
                        modifier = Modifier
                            .size(300.dp)
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = cartItem.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Review",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = cartItem.subtitle,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Are you sure you want to buy this book?") },
                confirmButton = {
                    Button(
                        onClick = {
                            cartViewModel.purchaseItem(cartItem.id) // Removes the book after purchasing it
                            showDialog = false
                            navController.navigate("confirmation") // navigates to ConfirmScreen
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4E6C1))
                    ) {
                        Text("Confirm", color = Color.Black)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF704214))
                    ) {
                        Text("Cancel", color = Color.White)
                    }
                },
                containerColor = Color(0xFFC0AB82),
                textContentColor = Color.White
            )
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Book not found",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            )
        }
    }
}

