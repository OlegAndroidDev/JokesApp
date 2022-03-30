package com.example.jokesapp.viewmodel

sealed class ResultState {
    object LOADING: ResultState()
    class SUCCESS<T>(val jokes : T) : ResultState()
    class ERROR(val throwable: Throwable) : ResultState()
    object DEFAULT : ResultState()
}