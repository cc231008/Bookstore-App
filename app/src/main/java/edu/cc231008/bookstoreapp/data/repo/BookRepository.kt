package edu.cc231008.bookstoreapp.data.repo

import android.util.Log
import edu.cc231008.bookstoreapp.data.db.BookDAO
import edu.cc231008.bookstoreapp.data.db.BookEntity
import edu.cc231008.bookstoreapp.data.remote.BookRemoteService
import kotlinx.coroutines.flow.map

class BookRepository(
    private val bookDAO: BookDAO,
    private val bookRemoteService: BookRemoteService
) {
    val books = bookDAO.getAllBooks()
        .map { bookList ->
            bookList.map { entity ->
                BookTemplate(
                    title = entity.title,
                    subtitle = entity.subtitle,
                    isbn13 = entity.isbn13,
                    price = entity.price,
                    image = entity.image,
                    url = entity.url
                )
            }
        }

    suspend fun loadApiToDatabase() {
        try {
            val bookResponse = bookRemoteService.getNewBooks()
            println("Books Response: ${bookResponse.books}")
            if (bookResponse.error == "0") {
                // Map each BookDto to a BookEntity
                val bookEntities = bookResponse.books.map { dto ->
                    BookEntity(
                        title = dto.title,
                        subtitle = dto.subtitle,
                        isbn13 = dto.isbn13,
                        price = dto.price,
                        image = dto.image,
                        url = dto.url
                    )
                }

                // Insert all entities into the database
                bookDAO.insertBooks(bookEntities)
            } else {
                Log.e("LoadApiToDatabase", "API returned error: ${bookResponse.error}")
            }
        } catch (e: Exception) {
            Log.e("LoadApiToDatabase", "Exception occurred: ${e.message}")
        }
    }
}