package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231008.bookstoreapp.data.db.CommentEntity
import edu.cc231008.bookstoreapp.data.repo.BookRepository
import edu.cc231008.bookstoreapp.data.repo.BookTemplate
import edu.cc231008.bookstoreapp.data.repo.CartTemplate
import edu.cc231008.bookstoreapp.data.repo.CommentTemplate
import edu.cc231008.bookstoreapp.data.repo.WishlistTemplate
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
    private val _bookDetailUiState = MutableStateFlow(
        BookDetailUiState(
            BookTemplate(
                title = "",
                subtitle = "",
                isbn13 = "",
                price = "",
                image = "",
                url = ""
            )
        )
    )
    val bookDetailUiState = _bookDetailUiState.asStateFlow()

    private val _comments = MutableStateFlow<List<CommentTemplate>>(emptyList())
    val comments = _comments.asStateFlow()

    init {
        viewModelScope.launch {
            val book = repository.fetchBookById(bookId)
            _bookDetailUiState.update { it.copy(book = book) }
            updateComments()
        }
    }

    // Fetch comments for the current book
    suspend fun updateComments() {
        val commentsUpdated = repository.getCommentsForBook(bookId)
        _comments.value = commentsUpdated
    }

    // This function adds a book to the wishlist
    fun addBookToWishlist(book: WishlistTemplate) {
        viewModelScope.launch {
            repository.insertIntoWishlist(book = book)
        }
    }

    // This function adds a book to the cart
    fun addBookToCart(book: CartTemplate) {
        viewModelScope.launch {
            repository.insertIntoCart(book = book)
        }
    }

    // Remove book from wishlist
    fun removeFromWishlist(isbn13: String) {
        viewModelScope.launch {
            repository.deleteWishlist(isbn13)
        }
    }

        // Check if book is in wishlist
        suspend fun isInWishlist(isbn13: String): Boolean {
            return repository.getWishlistItemByIsbn13(isbn13) != null
        }

        // Add a comment to the current book
        fun addComment(comment: String) {
            viewModelScope.launch {
                repository.addComment(
                    isbn13 = bookId,
                    comment = comment,
                )
                updateComments()
            }
        }

        // Delete a comment for the current book
        fun deleteComment(comment: CommentEntity) {
            viewModelScope.launch {
                repository.deleteComment(comment)
                updateComments()
            }
        }
    }