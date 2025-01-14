package edu.cc231008.bookstoreapp.data.remote

import retrofit2.http.GET

//Analogically to DAO, this part is responsible for managing data, but from the external API instead of Database.
interface BookRemoteService {
    //This part fetches all new books from the API
    @GET("new")
    suspend fun getNewBooks(): BookResponse
}