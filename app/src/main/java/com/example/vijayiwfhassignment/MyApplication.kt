package com.example.vijayiwfhassignment

import android.app.Application
import com.example.vijayiwfhassignment.di.appModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }

}