package edu.cc231008.bookstoreapp

import android.app.Application
import edu.cc231008.bookstoreapp.data.repo.BookRepository
import edu.cc231008.bookstoreapp.data.db.BookDatabase
import edu.cc231008.bookstoreapp.data.remote.BookRemoteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookApplication: Application() {

    val booksDao by lazy { BookDatabase.getDatabase(this).bookDao() }

    val bookRepository by lazy {
        //Retrofit is initialized with a base URL in the code bellow:
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.itbook.store/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //"retrofit" instance is used here to create ContactRemoteService, which is the service that will help us make network requests.
        val bookRemoteService = retrofit.create(BookRemoteService::class.java)

        BookRepository(booksDao, bookRemoteService)
    }
}