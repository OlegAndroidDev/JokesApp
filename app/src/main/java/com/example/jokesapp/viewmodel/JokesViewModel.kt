package com.example.jokesapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokesapp.rest.JokesApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JokesViewModel(
    private val jokesApiRepository: JokesApiRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _jokeMutable : MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val jokeLiveData : LiveData<ResultState> get() = _jokeMutable

    fun getRandomJoke(
    ) {
//        _joke.postValue(JokeState.LOADING)
        Log.d("view model initial", jokeLiveData.value.toString())
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = jokesApiRepository.getRandomJoke()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _jokeMutable.postValue(ResultState.SUCCESS(it))
                        Log.d("view model after", jokeLiveData.value.toString())
                    }?: throw Exception("Response is Null")
                }
                else {
                    throw Exception("Unsuccessful Response")
                }
            }
            catch (e: Exception) {
                _jokeMutable.postValue(ResultState.ERROR(e))
            }
        }
    }
    fun resetState() {
        _jokeMutable.postValue(ResultState.DEFAULT)
    }
//    fun getJokeWithName(firstName: String?, lastName: String?) {
//        viewModelScope.launch (ioDispatcher){
//            try {
//                val response = jokesApiRepository.getJokeWithName(firstName,lastName)
//                Log.d("****** Joke view model", "model")
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        withContext(Dispatchers.Main){
//                            _jokeMutable.value=(ResultState.SUCCESS(it))
//                        }
//                        //_jokeMutable.postValue(ResultState.SUCCESS(it))
//
//                    }?: throw Exception("Response is null")
//                }
//                else {
//                    throw Exception("No success in response")
//                }
//            }
//            catch (e: Exception) {
//                _jokeMutable.postValue(ResultState.ERROR(e))
//            }
//        }
//    }
//    fun resetState() {
//        _jokeMutable.postValue(ResultState.DEFAULT)
//    }
}