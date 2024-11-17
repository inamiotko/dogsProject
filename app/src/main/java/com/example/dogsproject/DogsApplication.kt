package com.example.dogsproject

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class DogsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DogsApplication)
            modules(appModule)
            printLogger(Level.DEBUG)
        }
    }
}