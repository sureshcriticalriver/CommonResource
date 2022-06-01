package com.common.resource.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.*
import com.common.resource.R
import com.common.resource.model.Products
import com.common.resource.utils.Utilities
import com.common.resource.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity  : AppCompatActivity() {
   private lateinit var mainViewModel : ProductsViewModel

    var listProducts: MutableLiveData<Products> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         mainViewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)

    }
}