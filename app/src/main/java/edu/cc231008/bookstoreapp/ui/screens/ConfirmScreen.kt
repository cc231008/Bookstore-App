package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.cc231008.bookstoreapp.R

@Composable
fun ConfirmationScreen(
    navController: NavHostController // used for the navigation back to HomeScreen after tapping
) {
    // Screen Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC))
            .clickable {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true } // back to HomeScreen and remove books from the backstack
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Check-Icon
            Image(
                painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                contentDescription = "Purchase Confirmed",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Confirmation Text
            Text(
                text = "Purchase Confirmed",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            // Tap to Continue Text
            Text(
                text = "Tap to continue",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            )
        }
    }
}
