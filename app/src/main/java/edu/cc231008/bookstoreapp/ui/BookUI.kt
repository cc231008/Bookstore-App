package edu.cc231008.bookstoreapp.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.cc231008.bookstoreapp.data.repo.BookTemplate


@Composable
fun BookUi() {
    ListOfBooks()
}


@Composable
fun ListOfBooks(
    bookViewModel: BookViewModel = viewModel(factory = AppViewModelProvider.Factory)
    ) {
    val state by bookViewModel.bookUiState.collectAsStateWithLifecycle()

    Log.d("BookUi", "Fetched Books: $state")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        itemsIndexed(state) { index, books ->
            BookCard(
                book = books,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun BookCard(
    book: BookTemplate,
    modifier: Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Text(
                text = "Book Title: ${book.title}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
