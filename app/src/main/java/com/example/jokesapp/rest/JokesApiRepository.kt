package com.example.jokesapp.rest

import android.util.Log
import com.example.jokesapp.model.Joke
import com.example.jokesapp.model.RandomJoke
import retrofit2.Response

class JokesApiRepositoryImpl(
    private val jokeApi: JokeApi
) : JokesApiRepository{

    override suspend fun getRandomJoke(): Response<RandomJoke> {
        return jokeApi.getRandomJoke()
    }

    override suspend fun getJokeWithName(firstName: String, lastName: String): Response<RandomJoke> {
        return jokeApi.getJokeWithName(firstName = firstName, lastName = lastName)
    }
}

interface JokesApiRepository{
    suspend fun getRandomJoke():Response<RandomJoke>

    suspend fun getJokeWithName(
                firstName: String,
                lastName: String
    ):Response<RandomJoke>
}