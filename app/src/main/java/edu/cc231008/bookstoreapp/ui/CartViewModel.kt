package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231008.bookstoreapp.data.repo.BookRepository
import edu.cc231008.bookstoreapp.data.repo.CartTemplate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartTemplate>>(emptyList())
    val cartItems: StateFlow<List<CartTemplate>> = _cartItems

    init {
        loadCartItems()
    }

    // Load cart items from the repository
    private fun loadCartItems() {
        viewModelScope.launch {
            _cartItems.value = bookRepository.getCartItems()
        }
    }

    fun addToCart(book: CartTemplate) {
        viewModelScope.launch {
            bookRepository.insertIntoCart(book)
            _cartItems.value = bookRepository.getCartItems()
        }
    }
    // Remove an item from the cart
    fun removeFromCart(cartItemId: Int) {
        viewModelScope.launch {
            bookRepository.removeCartItemById(cartItemId)
            _cartItems.value = bookRepository.getCartItems()
        }
    }
    fun purchaseItem(cartItemId: Int) {
        viewModelScope.launch {
            removeFromCart(cartItemId)
        }
    }
}