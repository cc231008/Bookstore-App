package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.cc231008.bookstoreapp.BookApplication

//This part provides ViewModels that will have necessary data from the Repository to display it on the screen.
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val bookApplication = this[APPLICATION_KEY] as BookApplication
            BookViewModel(bookApplication.bookRepository)
        }
    }
}