package edu.cc231008.bookstoreapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.cc231008.bookstoreapp.ui.AppViewModelProvider
import edu.cc231008.bookstoreapp.ui.CheckoutViewModel

@Composable
fun CheckoutScreen(
    checkoutViewModel: CheckoutViewModel = viewModel(factory = AppViewModelProvider.checkoutFactory),
) {
    val state = checkoutViewModel.cart.collectAsStateWithLifecycle()
    val cart = state.value

    Column {
        Text(cart.title)
        Text(cart.subtitle)
        Text(cart.price)
    }
}
