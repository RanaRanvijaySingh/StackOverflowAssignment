package com.example.stackoverflow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StackOverflowApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()

    }
}
