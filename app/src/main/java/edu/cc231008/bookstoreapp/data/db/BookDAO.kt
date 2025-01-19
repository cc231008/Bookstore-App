package edu.cc231008.bookstoreapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//We use dao to control the database by fetching, inserting, updating or deleting data.
@Dao
interface BookDAO {
    //Shows the list of all books. Flow helps to
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

    //This function fetches a book by its ISBN (=id)
    @Query("SELECT * FROM books WHERE isbn13 = :isbn13")
    suspend fun getBookById(isbn13: String): BookEntity

    //This insert method is used to save data in the database taken from API
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)

    //This function searches for books by title (searchQuery)
    @Query("SELECT * FROM books WHERE title LIKE '%' || :searchQuery || '%' ORDER BY title ASC")
    suspend fun searchBooksByTitle(searchQuery: String): List<BookEntity>

    //This function fetches all books in the wishlist list
    @Query("SELECT * FROM wishlist")
    suspend fun getBooksFromWishlist(): List<WishlistEntity>

    //This function inserts a book into the wishlist list
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoWishlist(wishlist: WishlistEntity)

    // Check if a book is in the wishlist by ISBN
    @Query("SELECT * FROM wishlist WHERE isbn13 = :isbn13 LIMIT 1")
    suspend fun getWishlistItemByIsbn13(isbn13: String): WishlistEntity?

    // Delete a book from the wishlist by ISBN
    @Query("DELETE FROM wishlist WHERE isbn13 = :isbn13")
    suspend fun deleteWishlistByIsbn13(isbn13: String)

    //This function gets the list of comments for a specific book by its isbn13 (which is book's ID)
    @Query("SELECT * FROM comments WHERE isbn13 = :isbn13")
    suspend fun getCommentsByIsbn13(isbn13: String): List<CommentEntity>

    //This function gets a comment by its id. It is used to fetch data of a specific comment, which is then edited.
    @Query("SELECT * FROM comments WHERE id = :id")
    suspend fun getCommentById(id: String): CommentEntity

    //This function inserts a comment into the database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity)

    //This function deletes a comment from the database.
    @Delete
    suspend fun deleteComment(comment: CommentEntity)

    //This function updates a comment in the database. It is used to edit a text of the comment.
    @Update
    suspend fun updateComment(comment: CommentEntity)

    //This function fetches all books for the cart list
    @Query("SELECT * FROM cart")
    suspend fun getCartItems(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity)

    @Query("SELECT * FROM cart WHERE id = :id")
    suspend fun getCartById(id: String): CartEntity

    @Query("DELETE FROM cart WHERE id = :cartItemId")
    suspend fun deleteCartItemById(cartItemId: Int)
}

/*
!! WHEN WE SHOULD ADD suspend? !!
1. One-shot Write Queries - Insert, Update, and Delete operations that modify data in the database.
You need suspend here because it typically has a lot of operations.
With suspend, they can be run on a background thread using coroutines, keeping the main thread free and avoiding potential freezes.
2. One-shot Read Queries - Simple read operations that return data only once, not continuously observed.
You need suspend here because it typically has a lot of operations.
3. Observable Read Queries - Continuous read operations that observe changes in the database and emit updates automatically whenever the data changes.
These functions should not be marked as suspend because Flow and LiveData are already designed to operate asynchronously.
 */