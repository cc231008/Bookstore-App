package edu.cc231008.bookstoreapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231008.bookstoreapp.data.db.CommentEntity
import edu.cc231008.bookstoreapp.data.repo.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditCommentViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BookRepository,
): ViewModel() {
    private val commentId: String = checkNotNull(savedStateHandle["commentId"])

    private val _comment = MutableStateFlow<CommentEntity?>(null)
    val comment = _comment.asStateFlow()

    init {
        viewModelScope.launch {
            val comment = repository.getCommentById(commentId)
            _comment.value = comment
        }
    }

    fun editComment(comment: CommentEntity) {
        viewModelScope.launch {
            repository.editComment(comment)
        }
    }
}