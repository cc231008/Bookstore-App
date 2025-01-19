package edu.cc231008.bookstoreapp.data.repo

import android.util.Log
import edu.cc231008.bookstoreapp.data.db.BookDAO
import edu.cc231008.bookstoreapp.data.db.BookEntity
import edu.cc231008.bookstoreapp.data.db.CartEntity
import edu.cc231008.bookstoreapp.data.db.CommentEntity
import edu.cc231008.bookstoreapp.data.db.WishlistEntity
import edu.cc231008.bookstoreapp.data.remote.BookRemoteService
import kotlinx.coroutines.flow.map

class BookRepository(
    private val bookDAO: BookDAO,
    private val bookRemoteService: BookRemoteService
) {

    //This function loads data from API to Database, so that data is available even if there is no internet connection
    suspend fun loadApiToDatabase() {
        try {
            val bookResponse = bookRemoteService.getNewBooks()
            println("Books Response: ${bookResponse.books}")
            if (bookResponse.error == "0") {
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

    //This variable that contains the list of all new books.
    var books = bookDAO.getAllBooks()
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

    //This function searches for books by title (searchQuery)
    suspend fun searchBooks(searchQuery: String): List<BookTemplate> {
        val books = bookDAO.searchBooksByTitle(searchQuery)

        return books.map { entity ->
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

    // This function fetches a book by its ISBN13
    suspend fun fetchBookById(isbn13: String): BookTemplate {
        val book = bookDAO.getBookById(isbn13)
        return BookTemplate(
            title = book.title,
            subtitle = book.subtitle,
            isbn13 = book.isbn13,
            price = book.price,
            image = book.image,
            url = book.url
        )
    }

    // This function fetches all books from the wishlist
    suspend fun fetchWishlistBooks(): List<WishlistTemplate> {
        return bookDAO.getBooksFromWishlist().map { entity ->
            WishlistTemplate(
                id = entity.id,
                isbn13 = entity.isbn13,
                title = entity.title,
                subtitle = entity.subtitle,
                price = entity.price,
                image = entity.image,
                url = entity.url
            )
        }
    }

    // This function fetches all comments for a specific book
    suspend fun getCommentsForBook(isbn13: String): List<CommentTemplate> {
        return bookDAO.getCommentsByIsbn13(isbn13).map { entity ->
            CommentTemplate(
                id = entity.id,
                isbn13 = entity.isbn13,
                comment = entity.comment
            )

        }
    }

        // Delete a book from the wishlist
        suspend fun deleteWishlist(isbn13: String) {
            bookDAO.deleteWishlistByIsbn13(isbn13)
        }

        suspend fun getWishlistItemByIsbn13(isbn13: String): WishlistEntity? {
            return bookDAO.getWishlistItemByIsbn13(isbn13)
        }

        suspend fun getCommentById(id: String): CommentEntity {
            return bookDAO.getCommentById(id)
        }

        suspend fun addComment(isbn13: String, comment: String) {
            bookDAO.insertComment(
                CommentEntity(
                    isbn13 = isbn13,
                    comment = comment
                )
            )
        }

        suspend fun deleteComment(comment: CommentEntity) {
            bookDAO.deleteComment(comment)
        }

        suspend fun editComment(comment: CommentEntity) {
            bookDAO.updateComment(comment)
        }

        // This function fetches all books in the cart list
        suspend fun getCartItems(): List<CartTemplate> {
            return bookDAO.getCartItems().map { entity ->
                CartTemplate(
                    id = entity.id,
                    isbn13 = entity.isbn13,
                    title = entity.title,
                    subtitle = entity.subtitle,
                    price = entity.price,
                    image = entity.image,
                    url = entity.url
                )
            }
        }

        // This function inserts a book into the wishlist list
        suspend fun insertIntoWishlist(book: WishlistTemplate) {
            bookDAO.insertIntoWishlist(
                WishlistEntity(
                    id = 0,
                    isbn13 = book.isbn13,
                    title = book.title,
                    subtitle = book.subtitle,
                    price = book.price,
                    image = book.image,
                    url = book.url
                )
            )
        }

        // This function inserts a book into the cart list
        suspend fun insertIntoCart(book: CartTemplate) {
            bookDAO.insertCart(
                CartEntity(
                    id = 0,
                    isbn13 = book.isbn13,
                    title = book.title,
                    subtitle = book.subtitle,
                    price = book.price,
                    image = book.image,
                    url = book.url
                )
            )
        }

        suspend fun getCartById(id: String): CartTemplate {
            val cart = bookDAO.getCartById(id)
            return CartTemplate(
                id = cart.id,
                isbn13 = cart.isbn13,
                title = cart.title,
                subtitle = cart.subtitle,
                price = cart.price,
                image = cart.image,
                url = cart.url
            )
        }

        suspend fun removeCartItemById(cartItemId: Int) {
            bookDAO.deleteCartItemById(cartItemId)
        }
    }
