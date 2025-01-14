package edu.cc231008.bookstoreapp.data.remote

//The structure of data from API.
data class BookDto(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    val image: String,
    val url: String
)

//BookResponse holds BookDto as well as error messages in case of fetching failure.
data class BookResponse(
    val error: String,
    val total: String,
    val books: List<BookDto>
)

