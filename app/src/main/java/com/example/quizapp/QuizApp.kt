package com.example.quizapp

import android.app.Application
import com.example.quizapp.di.databaseModule
import com.example.quizapp.di.repositoryModule
import com.example.quizapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class QuizApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@QuizApp)
            modules(databaseModule, repositoryModule, viewModelModule)
        }

    }
}