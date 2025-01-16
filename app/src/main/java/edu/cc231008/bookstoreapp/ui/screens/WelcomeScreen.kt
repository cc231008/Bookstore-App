package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.cc231008.bookstoreapp.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    navController: NavHostController
) {
    // States to control the visibility of the welcome screen and loading screen
    var showWelcomeScreen by remember { mutableStateOf(true) }
    var showLoadingScreen by remember { mutableStateOf(false) }

    // A full-screen container with a beige background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC))
    ) {
        // This shows the welcome screen with a fade-in and fade-out animation
        AnimatedVisibility(
            visible = showWelcomeScreen,
            enter = fadeIn(animationSpec = tween(1000)), // Smooth fade-in over 1 second
            exit = fadeOut(animationSpec = tween(1000))  // Smooth fade-out over 1 second
        ) {
            WelcomeContent {
                // When the user taps the screen, hide the welcome screen and show the loading screen
                showWelcomeScreen = false
                showLoadingScreen = true
            }
        }

        // If the loading screen should be displayed
        if (showLoadingScreen) {
            // This effect triggers the navigation to the home screen after a delay
            LaunchedEffect(Unit) {
                delay(2000) // Simulates a loading time of 2 seconds
                navController.navigate("home") {
                    popUpTo("welcome") { inclusive = true } // Remove welcome screen from the back stack
                }
            }

            // Display a centered loading spinner
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Gray)
            }
        }
    }
}

// Displays the content of the welcome screen
@Composable
fun WelcomeContent(onTap: () -> Unit) {
    // A column to vertically arrange the elements on the welcome screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onTap() }, // Tapping anywhere triggers the provided action
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Center items both vertically and horizontally
    ) {
        // Displays the welcome image
        Image(
            painter = painterResource(id = R.drawable.welcome_screen_book),
            contentDescription = "Welcome Book Image",
            modifier = Modifier.size(300.dp) // Sets the size of the image
        )

        Spacer(modifier = Modifier.height(8.dp)) // Adds spacing between elements

        // Displays the app title
        Text(
            text = "Bookstore",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 48.sp, // Sets the font size
                fontWeight = FontWeight.SemiBold // Makes the text bold
            ),
            color = Color.Black, // Text color
            textAlign = TextAlign.Center // Aligns the text to the center
        )

        Spacer(modifier = Modifier.height(16.dp)) // Adds spacing between elements

        // Displays a welcome message
        Text(
            text = "Welcome to our bookstore app.\nHere you can order any book you want!",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), // Font styling
            color = Color.DarkGray, // Text color
            textAlign = TextAlign.Center, // Aligns the text to the center
            modifier = Modifier.padding(horizontal = 32.dp) // Adds horizontal padding for better readability
        )

        Spacer(modifier = Modifier.height(200.dp)) // Adds a large gap before the next element

        // Displays the instruction to tap
        Text(
            text = "Tap to continue",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 20.sp), // Font styling
            color = Color.Gray, // Text color
            textAlign = TextAlign.Center // Aligns the text to the center
        )
    }
}
