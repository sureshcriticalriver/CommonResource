package com.example.myapplication.utils

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.example.myapplication.utils.MyApplication
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import kotlin.jvm.Synchronized

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