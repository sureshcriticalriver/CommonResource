package com.example.myapplication.utils

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.example.myapplication.utils.MyApplication
import androidx.multidex.MultiDex
import kotlin.jvm.Synchronized

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
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