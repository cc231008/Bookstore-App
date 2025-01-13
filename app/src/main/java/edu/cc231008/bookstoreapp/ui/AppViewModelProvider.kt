package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.cc231008.bookstoreapp.BookApplication


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val bookApplication = this[APPLICATION_KEY] as BookApplication
            BookViewModel(bookApplication.bookRepository)
        }
    }
}