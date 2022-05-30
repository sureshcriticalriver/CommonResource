package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.utils.Utilities

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Utilities.logD("--Debug--")
        Utilities.logE("--Error--")
        Utilities.logI("---Log I---")
        Utilities.logV("---Log V--")
        Utilities.logEwithoutLogStore("---LogE without log store ")


    }
}