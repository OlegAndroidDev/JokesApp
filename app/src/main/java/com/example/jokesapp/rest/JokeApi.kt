package com.example.jokesapp.rest

import com.example.jokesapp.model.Joke
import com.example.jokesapp.model.RandomJoke
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokeApi {

    @GET("$NUMBER_PATH{number}")
    suspend fun getRandomJoke(
        @Path("number") number : Int = 1
        ):Response<RandomJoke>

    @GET("$NUMBER_PATH{number}")
    suspend fun getNumberJoke(
        @Path("number") number : Int = 1
    ):Response<RandomJoke>

    @GET("$NUMBER_PATH{number}")
    suspend fun getJokeWithName(
        @Path("number") number : Int = 1,
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String
    ): Response<RandomJoke>

    companion object{
        //http://api.icndb.com/jokes/random?firstName=John&lastName=Doe
        const val BASE_URL = "https://api.icndb.com/jokes/"
        const val RANDOM_PATH = "random"
        const val NUMBER_PATH = "random/"
    }
}