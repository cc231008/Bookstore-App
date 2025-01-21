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
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val commentEdited = savedStateHandle?.get<Boolean>("commentEdited") ?: false
    LaunchedEffect(commentEdited) {
        if (commentEdited) {
            bookDetailViewModel.updateComments()
            savedStateHandle?.set("commentEdited", false)
        }
    }

    var isWishlist by remember { mutableStateOf(false) }
    var isAddedToCart by remember { mutableStateOf(false) }

    LaunchedEffect(book.isbn13) {
        isWishlist = bookDetailViewModel.isInWishlist(book.isbn13)
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC))
            .verticalScroll(scrollState)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFF745447)),
        ) {
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

        // Book cover and wishlist icon
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
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
                    isWishlist = !isWishlist
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (isWishlist) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isWishlist) "Remove from Wishlist" else "Add to Wishlist",
                    tint = if (isWishlist) Color(0xFF8B0000) else Color.Gray,
                    modifier = Modifier.size(35.dp)
                )
            }

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
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Book Autors
        if (book.authors.isNotBlank()) {
            Text(
                text = "Written by ${book.authors}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        // Pages and Rating with star
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pages Box
            Card(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(width = 100.dp, height = 40.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFCCB78F)),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "${book.pages} Pages",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
            // Rating box
            Card(
                modifier = Modifier
                    .size(width = 100.dp, height = 40.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFCCB78F)),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${book.rating}/5",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            ),
                            textAlign = TextAlign.Center
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "Rating Star",
                            tint = Color(0xFFA6761D),
                            modifier = Modifier
                                .size(28.dp)
                                .padding(start = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Description
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFCCB78F)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Text(
                text = android.text.Html.fromHtml(book.desc, android.text.Html.FROM_HTML_MODE_COMPACT).toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    lineHeight = 30.sp,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

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
                        color = if (isAddedToCart) Color(0xFF745447) else Color(0xFFe6e1cf),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isAddedToCart) Color(0xFF5E3221) else Color(0xFFB87333),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        if (!isAddedToCart) {
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
                            isAddedToCart = true
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isAddedToCart) "Added to Cart" else "Add to Cart",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isAddedToCart) Color.White else Color.Black
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
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
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
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFe6decf)),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
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
                        color = Color(0xFF745447),
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
                val createdComment = formatTimestamp(comment.createdAt)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFe6decf)),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Added at: $createdComment",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = comment.comment,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                        )
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
                                            comment = commentText,
                                            createdAt = comment.createdAt
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
                                            comment = commentText,
                                            createdAt = comment.createdAt
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
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
}

//As time of comment's creation is originally received in milliseconds, formatTimestamp helps to format milliseconds into date.
fun formatTimestamp(timestamp: Long): String {
    //If timestamp is 1672531199000, instant would represent 2023-01-01T00:00:00Z.
    val instant = Instant.ofEpochMilli(timestamp)
    //Uses pattern to display in certain way
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm")
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}