package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231008.bookstoreapp.data.repo.BookRepository
import edu.cc231008.bookstoreapp.data.repo.BookTemplate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {
    private val _bookUiState = MutableStateFlow<List<BookTemplate>>(emptyList())
    val bookUiState: StateFlow<List<BookTemplate>> = _bookUiState

    init {
        viewModelScope.launch {
            repository.loadApiToDatabase()
            // Collect the list of books from the repository and update the UI state
            repository.books.collect { books ->
                _bookUiState.value = books
            }
        }
    }

    // Function to search for books
    fun searchBooks(searchQuery: String) {
        viewModelScope.launch {
            // This variable stores a sorted list of books based on the search query
            val books = repository.searchBooks(searchQuery)
            // Update the UI state with the search results
            _bookUiState.value = books
        }
        }
    }