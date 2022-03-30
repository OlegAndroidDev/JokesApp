package com.example.jokesapp

import android.app.Application
import com.example.jokesapp.di.networkModule
import com.example.jokesapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JokesApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JokesApp)
            modules(listOf(networkModule, viewModelModule))
        }
    }

}