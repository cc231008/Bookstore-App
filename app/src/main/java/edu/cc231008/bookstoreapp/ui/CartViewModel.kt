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
): ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartTemplate>>(emptyList())
    val cartItems: StateFlow<List<CartTemplate>> = _cartItems

    init {
        viewModelScope.launch {
            _cartItems.value = bookRepository.getCartItems()
        }
    }
}