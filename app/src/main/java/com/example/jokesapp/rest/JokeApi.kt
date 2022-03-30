package com.example.jokesapp.rest

import com.example.jokesapp.model.Joke
import com.example.jokesapp.model.RandomJoke
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApi {

    @GET(RANDOM_PATH)
    suspend fun getRandomJoke(
    ):Response<RandomJoke>

    @GET(RANDOM_PATH)
    suspend fun getJokeWithName(
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String
    ): Response<RandomJoke>

    companion object{
        //http://api.icndb.com/jokes/random?firstName=John&lastName=Doe
        const val BASE_URL = "https://api.icndb.com/jokes/"
        const val RANDOM_PATH = "random"
    }
}