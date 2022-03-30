package com.example.jokesapp.model


import com.google.gson.annotations.SerializedName

data class RandomJoke(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val joke: Joke
)