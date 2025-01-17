package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231008.bookstoreapp.data.db.CommentEntity
import edu.cc231008.bookstoreapp.data.db.WishlistEntity
import edu.cc231008.bookstoreapp.data.repo.BookRepository
import edu.cc231008.bookstoreapp.data.repo.BookTemplate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BookDetailUiState(
    val book: BookTemplate
)

class BookDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BookRepository,
) : ViewModel() {

    private val bookId: String = checkNotNull(savedStateHandle["bookId"])

    private val _bookDetailUiState = MutableStateFlow(BookDetailUiState(BookTemplate(
            title = "",
            subtitle = "",
            isbn13 = "",
            price = "",
            image = "",
            url = ""
    ))
    )
    val bookDetailUiState = _bookDetailUiState.asStateFlow()

    private val _comments = MutableStateFlow<List<CommentEntity>>(emptyList())
    val comments = _comments.asStateFlow()

    private suspend fun updateComments() {
            val commentsUpdated = repository.getCommentsForBook(bookId)
            _comments.value = commentsUpdated
    }

    init {
            viewModelScope.launch {
                val book = repository.fetchBookById(bookId)
                _bookDetailUiState.update {
                    it.copy(book = book)
                }
            }
        viewModelScope.launch {
        updateComments()
            }
    }

    fun addBookToWishlist(book: WishlistEntity) {
        viewModelScope.launch {
            repository.insertWishlist(
                isbn13 = book.isbn13,
                title = book.title,
                subtitle = book.subtitle,
                price = book.price,
                image = book.image,
                url = book.url
            )
        }
    }

    fun addComment(comment: String) {
        viewModelScope.launch {
            repository.addComment(
                isbn13 = bookId,
                comment = comment
            )
            updateComments()
        }
    }
    fun deleteComment(comment: CommentEntity) {
        viewModelScope.launch {
            repository.deleteComment(comment)
            updateComments()
        }
    }

}