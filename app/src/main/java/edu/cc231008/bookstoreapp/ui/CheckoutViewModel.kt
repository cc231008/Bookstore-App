package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231008.bookstoreapp.data.repo.BookRepository
import edu.cc231008.bookstoreapp.data.repo.CartTemplate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/*
data class CartUiState(
    val cart: CartTemplate
)

 */

class CheckoutViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository
): ViewModel() {
    private val cartId: String = checkNotNull(savedStateHandle["cartId"])

    private val _cart = MutableStateFlow(CartTemplate(
        id = 0,
        title = "",
        subtitle = "",
        isbn13 = "",
        price = "",
        image = "",
        url = ""
    ))
    val cart = _cart.asStateFlow()

    init {
        viewModelScope.launch {
            _cart.value = bookRepository.getCartById(cartId)
        }
    }
}