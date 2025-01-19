package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231008.bookstoreapp.data.repo.BookRepository
import edu.cc231008.bookstoreapp.data.repo.WishlistTemplate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WishListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _wishlistBooks = MutableStateFlow<List<WishlistTemplate>>(emptyList())
    val wishlistBooks: StateFlow<List<WishlistTemplate>> = _wishlistBooks

    init {
        loadWishlistBooks()
    }

    // Load Wishlist Books
    private fun loadWishlistBooks() {
        viewModelScope.launch {
            _wishlistBooks.value = bookRepository.fetchWishlistBooks()
        }
    }

    // Remove a book from the wishlist
    fun removeFromWishlist(isbn13: String) {
        viewModelScope.launch {
            bookRepository.deleteWishlist(isbn13)
            _wishlistBooks.value = bookRepository.fetchWishlistBooks()
        }
    }
}
