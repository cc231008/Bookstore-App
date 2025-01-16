package edu.cc231008.bookstoreapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//We use dao to control the database by fetching, inserting, updating or deleting data.
@Dao
interface BookDAO {
    //Shows the list of all books
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

    //In this project Insert method is used to save data taken from API
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)

    //This function searches for books by title (searchQuery)
    @Query("SELECT * FROM books WHERE title LIKE '%' || :searchQuery || '%' ORDER BY title ASC")
    suspend fun searchBooksByTitle(searchQuery: String): List<BookEntity>

    //This function fetches a book by its ISBN (=id)
    @Query("SELECT * FROM books WHERE isbn13 = :isbn13")
    suspend fun getBookById(isbn13: String): BookEntity

    @Query("SELECT * FROM wishlist")
    suspend fun getBookFromWishlist(): List<WishlistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlist(wishlist: WishlistEntity)
}