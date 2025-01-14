package edu.cc231008.bookstoreapp.data.remote

data class BookDto(
    val title: String,
    val subtitle: String,
    val isbn13: String, // Change to String to match the JSON
    val price: String,  // Change to String to match the JSON
    val image: String,
    val url: String
)

data class BookResponse(
    val error: String,
    val total: String,
    val books: List<BookDto>
)

