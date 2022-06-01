package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.*
import com.example.myapplication.R
import com.example.myapplication.model.Products
import com.example.myapplication.utils.Utilities
import com.example.myapplication.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity  : AppCompatActivity() {
   private lateinit var mainViewModel : ProductsViewModel

    var listProducts: MutableLiveData<Products> = MutableLiveData()

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