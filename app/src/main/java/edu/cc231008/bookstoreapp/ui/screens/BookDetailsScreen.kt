package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import edu.cc231008.bookstoreapp.R
import edu.cc231008.bookstoreapp.data.db.CommentEntity
import edu.cc231008.bookstoreapp.data.repo.CartTemplate
import edu.cc231008.bookstoreapp.data.repo.WishlistTemplate
import edu.cc231008.bookstoreapp.ui.AppViewModelProvider
import edu.cc231008.bookstoreapp.ui.BookDetailViewModel

@Composable
fun BookDetailsScreen(
    onEditComment: (CommentEntity) -> Unit,
    navController: NavHostController,
    bookDetailViewModel: BookDetailViewModel = viewModel(factory = AppViewModelProvider.DetailFactory),
) {
    // Collect a current state of a book from the ViewModel
    val state by bookDetailViewModel.bookDetailUiState.collectAsStateWithLifecycle()
    val book = state.book

    // Collect a list of comments from the ViewModel
    val comments by bookDetailViewModel.comments.collectAsStateWithLifecycle()
    var commentText by rememberSaveable { mutableStateOf("") }

    // This part of the code responsible for updating list of comments when the comment is edited.
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val commentEdited = savedStateHandle?.get<Boolean>("commentEdited") ?: false
    LaunchedEffect(commentEdited) {
        if (commentEdited) {
            bookDetailViewModel.updateComments()
            savedStateHandle?.set("commentEdited", false)
        }
    }
        // Load the wishlist status from ViewModel
        var isWishlist by remember { mutableStateOf(false) }

        var isAddedToCart by remember { mutableStateOf(false) }

        // On screen load, check if the book is in the wishlist
        LaunchedEffect(book.isbn13) {
            isWishlist = bookDetailViewModel.isInWishlist(book.isbn13)
        }

        // Scrollable column
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5DC))
                .verticalScroll(scrollState)
        ) {
            // Header with title
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(0xFF704214)),
            ) {
                // Title in the center
                Text(
                    text = "Bookstore",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Container for the book cover and heart icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Heart icon aligned to the top-left corner
                IconButton(
                    onClick = {
                        if (isWishlist) {
                            bookDetailViewModel.removeFromWishlist(book.isbn13)
                        } else {
                            bookDetailViewModel.addBookToWishlist(
                                WishlistTemplate(
                                    id = 0,
                                    isbn13 = book.isbn13,
                                    title = book.title,
                                    subtitle = book.subtitle,
                                    price = book.price,
                                    image = book.image,
                                    url = book.url
                                )
                            )
                        }
                        isWishlist = !isWishlist // Update the UI state
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = if (isWishlist) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isWishlist) "Remove from Wishlist" else "Add to Wishlist",
                        tint = if (isWishlist) Color(0xFF8B0000) else Color.Gray,
                        modifier = Modifier.size(35.dp)
                    )
                }

                // Book cover image
                Image(
                    painter = rememberAsyncImagePainter(book.image),
                    contentDescription = "Book Cover: ${book.title}",
                    modifier = Modifier
                        .size(400.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Book title
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Price: ${book.price}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )


            Spacer(modifier = Modifier.height(35.dp))

            // Add to Cart Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(48.dp)
                        .background(
                            color = if (isAddedToCart) Color(0xFFB87333) else Color(0xFFF4E6C1),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable {
                            if (!isAddedToCart) { // Prevent re-adding if already added
                                bookDetailViewModel.addBookToCart(
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
                                isAddedToCart = true // Update state
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isAddedToCart) "Added to Cart" else "Add to Cart",
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
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
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
                            innerTextField()
                        }
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (commentText.isNotBlank()) {
                            bookDetailViewModel.addComment(commentText)
                            commentText = ""
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFF704214),
                            shape = RoundedCornerShape(24.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_send_24),
                        contentDescription = "Send",
                        tint = Color.White
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
                                onClick = {
                                    onEditComment(
                                        CommentEntity(
                                            id = comment.id,
                                            isbn13 = comment.isbn13,
                                            comment = commentText
                                        )
                                    )
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = Color(
                                        0xFF704214
                                    )
                                )
                            ) {
                                Text("Edit")
                            }
                            TextButton(
                                onClick = {
                                    bookDetailViewModel.deleteComment(
                                        CommentEntity(
                                            id = comment.id,
                                            isbn13 = comment.isbn13,
                                            comment = commentText
                                        )
                                    )
                                },
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