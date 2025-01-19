package edu.cc231008.bookstoreapp.data.db

import androidx.room.Entity
import androidx.room.Index
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

//The table for wishlist
@Entity(
    tableName = "wishlist",
    indices = [Index(value = ["isbn13"], unique = true)]
)
data class WishlistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isbn13: String,
    val title: String,
    val subtitle: String,
    val price: String,
    val image: String,
    val url: String
)

//The table for cart
@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isbn13: String,
    val title: String,
    val subtitle: String,
    val price: String,
    val image: String,
    val url: String
)

@Entity(
    tableName = "comments",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["isbn13"],
            childColumns = ["isbn13"],
            onDelete = androidx.room.ForeignKey.CASCADE
        ),
    ]
)
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isbn13: String,
    val comment: String
)
