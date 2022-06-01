package com.example.myapplication.view

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    companion object {
        var single = MyApplication()

        fun getInstance(): MyApplication {
            if (single == null)
                single = MyApplication()
            return single
        }
    }

}