package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231008.bookstoreapp.data.repo.BookRepository
import edu.cc231008.bookstoreapp.data.repo.WishlistTemplate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WishListViewModel(private val repository: BookRepository) : ViewModel() {
    private val _wishlistBooks = MutableStateFlow<List<WishlistTemplate>>(emptyList())
    val wishlistBooks: StateFlow<List<WishlistTemplate>> = _wishlistBooks

    init {
        viewModelScope.launch {
            val wishlistBooks = repository.fetchWishlistBooks()
            _wishlistBooks.value = wishlistBooks
        }
    }
}