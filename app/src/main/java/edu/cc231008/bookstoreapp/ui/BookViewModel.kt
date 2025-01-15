package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import edu.cc231008.bookstoreapp.data.db.BookEntity
import edu.cc231008.bookstoreapp.data.repo.BookRepository
import edu.cc231008.bookstoreapp.data.repo.BookTemplate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


//This
class BookViewModel(private val repository: BookRepository) : ViewModel() {
    private val _bookUiState = MutableStateFlow<List<BookTemplate>>(emptyList())
    val bookUiState: StateFlow<List<BookTemplate>> = _bookUiState

    init {
        viewModelScope.launch {
            repository.loadApiToDatabase()
            repository.books.collect { books ->
                _bookUiState.value = books
            }
        }
    }

    private val _searchResults = MutableStateFlow<List<BookTemplate>>(emptyList())
    val searchResults: StateFlow<List<BookTemplate>> = _searchResults

    fun searchBooks(searchQuery: String) {
        viewModelScope.launch {
            val books = repository.searchBooks(searchQuery)
            _searchResults.value = books
            _bookUiState.value = books
        }
        }
    }