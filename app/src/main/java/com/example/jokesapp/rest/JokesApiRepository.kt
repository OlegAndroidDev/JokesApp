package com.example.jokesapp.rest

import android.util.Log
import com.example.jokesapp.model.Joke
import com.example.jokesapp.model.RandomJoke
import retrofit2.Response

class JokesApiRepositoryImpl(
    private val jokeApi: JokeApi
) : JokesApiRepository{

    override suspend fun getRandomJoke():
            Response<RandomJoke> {
        return jokeApi.getRandomJoke()
    }
    override suspend fun getJokeWithName(firstName: String, lastName: String): Response<RandomJoke> {
        return jokeApi.getJokeWithName(firstName = firstName, lastName = lastName)
    }

    override suspend fun getNumberJoke(number: Int): Response<RandomJoke> {
        return jokeApi.getNumberJoke(number)
    }
}

interface JokesApiRepository{
    suspend fun getRandomJoke():Response<RandomJoke>

    suspend fun getJokeWithName(
                firstName: String,
                lastName: String
    ):Response<RandomJoke>

    suspend fun getNumberJoke(number:Int = 20
    ):Response<RandomJoke>
}