package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.cc231008.bookstoreapp.R
import edu.cc231008.bookstoreapp.data.db.CartEntity
import edu.cc231008.bookstoreapp.data.db.CommentEntity
import edu.cc231008.bookstoreapp.data.db.WishlistEntity
import edu.cc231008.bookstoreapp.ui.AppViewModelProvider
import edu.cc231008.bookstoreapp.ui.BookDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    onEditComment: (CommentEntity) -> Unit,
    navController: NavHostController,
    bookDetailViewModel: BookDetailViewModel = viewModel(factory = AppViewModelProvider.DetailFactory),
) {
    val state by bookDetailViewModel.bookDetailUiState.collectAsStateWithLifecycle()
    val book = state.book
    val comments by bookDetailViewModel.comments.collectAsStateWithLifecycle()
    var commentText by remember { mutableStateOf("") }

    // Variables to track the state of the buttons
    var wishlistButtonClicked by remember { mutableStateOf(false) }
    var cartButtonClicked by remember { mutableStateOf(false) }

    // Add the visual structure and layout for the screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC)) // Background for the entire screen
    ) {
        // Header with title
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

        // Centered book title
        Text(
            text = book.title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Price: ${book.price}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally) // Centers the text
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Buttons for Wishlist and Shopping Cart
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Wishlist Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (wishlistButtonClicked) Color(0xFFB87333) else Color(0xFFF4E6C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .clickable { // Click action
                        bookDetailViewModel.addBookToWishlist(
                            WishlistEntity(
                                isbn13 = book.isbn13,
                                title = book.title,
                                subtitle = book.subtitle,
                                price = book.price,
                                image = book.image,
                                url = book.url
                            )
                        )
                        wishlistButtonClicked = true // Update state
                    },
                contentAlignment = Alignment.Center // Centers the text inside the button
            ) {
                Text(
                    text = if (wishlistButtonClicked) "The book was added to the wishlist" else "Add to Wishlist",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black // Text color
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add to Cart Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (cartButtonClicked) Color(0xFFB87333) else Color(0xFFF4E6C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .clickable { // Click action
                        bookDetailViewModel.addBookToCart(
                            CartEntity(
                                isbn13 = book.isbn13,
                                title = book.title,
                                subtitle = book.subtitle,
                                price = book.price,
                                image = book.image,
                                url = book.url
                            )
                        )
                        cartButtonClicked = true // Update state
                    },
                contentAlignment = Alignment.Center // Centers the text inside the button
            ) {
                Text(
                    text = if (cartButtonClicked) "The book was added to the cart" else "Add to Cart",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Comments section
        Text(
            text = "Comments:",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Add a new comment
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // White background text field
            Box(
                modifier = Modifier
                    .weight(1f) // Makes the TextField expand to fill the available space
                    .height(56.dp) // Increased height for the input field
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                BasicTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    decorationBox = { innerTextField ->
                        if (commentText.isEmpty()) {
                            Text(
                                text = "Add a comment...",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                    lineHeight = 20.sp
                                )
                            )
                        }
                        innerTextField() // Displays the actual text input
                    }
                )
            }

            Spacer(modifier = Modifier.width(8.dp)) // Space between the input field and the button

            // Send icon button
            IconButton(
                onClick = {
                    if (commentText.isNotBlank()) {
                        bookDetailViewModel.addComment(commentText)
                        commentText = "" // Clear the input field after sending
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFF704214), // Brown background for the icon button
                        shape = RoundedCornerShape(24.dp) // Circular button
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_send_24), // Replace with your send icon resource
                    contentDescription = "Send",
                    tint = Color.White // White icon color
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display comments
        comments.forEach { comment ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD7C5A1)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = comment.comment, style = MaterialTheme.typography.bodyMedium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = { onEditComment(comment) },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF704214))
                        ) {
                            Text("Edit")
                        }
                        TextButton(
                            onClick = { bookDetailViewModel.deleteComment(comment) },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}
