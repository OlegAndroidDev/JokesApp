package com.example.jokesapp.di

import com.example.jokesapp.model.Joke
import com.example.jokesapp.rest.JokeApi
import com.example.jokesapp.rest.JokeApi.Companion.BASE_URL
import com.example.jokesapp.rest.JokesApiRepository
import com.example.jokesapp.rest.JokesApiRepositoryImpl
import com.example.jokesapp.viewmodel.JokesViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

    fun providesJokeApi(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(JokeApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(JokeApi::class.java)

    
    fun providesDispatcher() = Dispatchers.IO

    single { providesLoggingInterceptor() }
    single { providesOkHttpClient(get()) }
    single { providesJokeApi(get()) }
    single { providesDispatcher() }
}

val viewModelModule = module {
    fun providesJokesApiRepo(jokesApi: JokeApi): JokesApiRepository = JokesApiRepositoryImpl(jokesApi)
    single { providesJokesApiRepo(get()) }
    viewModel {JokesViewModel(get(), get()) }
}

