package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.cc231008.bookstoreapp.data.db.CommentEntity
import edu.cc231008.bookstoreapp.ui.AppViewModelProvider
import edu.cc231008.bookstoreapp.ui.EditCommentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCommentScreen(
    commentId: String,
    navController: NavHostController,
    editCommentViewModel: EditCommentViewModel = viewModel(factory = AppViewModelProvider.editCommentFactory),
) {
    // Collect the current comment from the ViewModel
    val comment by editCommentViewModel.comment.collectAsStateWithLifecycle()
    var commentText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Ensure `commentText` is updated whenever the `comment` changes
    LaunchedEffect(comment) {
        commentText = comment?.comment ?: ""
    }

    Scaffold(
        containerColor = Color(0xFFF5F5DC),
        topBar = {
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
                        text = "Edit Comment",
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    Text(
                        text = "Edit Your Comment below",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF745447),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // TextField for comment editing
                    TextField(
                        value = commentText,
                        onValueChange = {
                            commentText = it
                            errorMessage = "" // Clear error message on change
                        },
                        label = { Text("Comment") },
                        placeholder = { Text("Enter your comment here...") },
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp) // Multiline support
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFFD7C5A1),
                            focusedIndicatorColor = Color(0xFF745447),
                            unfocusedIndicatorColor = Color(0xFFB0B0B0)
                        )
                    )

                    // Error message if the field is empty
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    // Save Button
                    Button(
                        onClick = {
                            if (commentText.isBlank()) {
                                errorMessage = "Comment cannot be empty."
                            } else {
                                comment?.let {
                                    editCommentViewModel.editComment(
                                        CommentEntity(
                                            id = commentId.toInt(),
                                            isbn13 = comment!!.isbn13,
                                            comment = commentText,
                                            createdAt = comment!!.createdAt
                                        )
                                    )
                                    // Notify the previous screen and navigate back
                                    navController.previousBackStackEntry?.savedStateHandle?.set("commentEdited", true)
                                    navController.popBackStack()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF745447),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Save", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }

                    // Cancel Button
                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(top = 8.dp)
                    ) {
                        Text("Cancel", fontSize = 16.sp, color = Color.Gray)
                    }
                }
            }
        }
    )
}
