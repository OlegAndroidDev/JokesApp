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

    val jokeMutable : MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val jokeLiveData : LiveData<ResultState> get() = jokeMutable

    fun getRandomJoke() {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = jokesApiRepository.getRandomJoke()
                if (response.isSuccessful) {
                    response.body()?.let {
                        jokeMutable.postValue(ResultState.SUCCESS(it))
                    }?: throw Exception("Response is Null")
                }
                else {
                    throw Exception("Unsuccessful Response")
                }
            }
            catch (e: Exception) {
                jokeMutable.postValue(ResultState.ERROR(e))
            }
        }
    }

    fun getJokeWithName(fName: String, lName:String) {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = jokesApiRepository.getJokeWithName(fName, lName)
                if (response.isSuccessful) {
                    response.body()?.let {
                        jokeMutable.postValue(ResultState.SUCCESS(it))
                    }?: throw Exception("Response is Null")
                }
                else {
                    throw Exception("Unsuccessful Response")
                }
            }
            catch (e: Exception) {
                jokeMutable.postValue(ResultState.ERROR(e))
            }
        }
    }

    fun InitJokeMutable() {
        jokeMutable.postValue(ResultState.LOADING)
    }
}