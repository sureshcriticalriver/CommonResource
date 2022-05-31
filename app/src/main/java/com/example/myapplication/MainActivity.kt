package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.*
import com.example.myapplication.model.Products
import com.example.myapplication.network.ApiStatus
import com.example.myapplication.utils.Utilities
import com.example.myapplication.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var listProducts: MutableLiveData<Products> = MutableLiveData()
    private lateinit var mainViewModel : ProductsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Utilities.logD("--Debug--")
        Utilities.logE("--Error--")
        Utilities.logI("---Log I---")
        Utilities.logV("---Log V--")
        Utilities.logEwithoutLogStore("---LogE without log store ")


         mainViewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)



    }
}