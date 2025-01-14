package edu.cc231008.bookstoreapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

//The table for books
@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val isbn13: String,
    val title: String,
    val subtitle: String,
    val price: String,
    val image: String,
    val url: String
)