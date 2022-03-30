package com.example.jokesapp.viewmodel

sealed class ResultState {
    object LOADING: ResultState()
    object INIT: ResultState()
    class SUCCESS<T>(val jokes : T) : ResultState()
    class ERROR(val throwable: Throwable) : ResultState()
}