package edu.cc231008.bookstoreapp.data.remote

import retrofit2.http.GET

interface BookRemoteService {
    @GET("new")
    suspend fun getNewBooks(): BookResponse
}