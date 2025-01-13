package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231008.bookstoreapp.data.repo.BookRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class BookViewModel(private val repository: BookRepository) : ViewModel() {
    val bookUiState = repository.books.stateIn(
        viewModelScope, //viewModelScope - automatically cancels coroutines when the ViewModel is cleared.
        SharingStarted.WhileSubscribed(5000), //keeps the state active while there are subscribers, with a delay of 5000 ms before stopping.
        emptyList() //The initial state.
    )
    //An init block launches a coroutine in the viewModelScope to load initial contacts from the repository.
    init {
        viewModelScope.launch {
            repository.loadApiToDatabase()
        }
    }
}