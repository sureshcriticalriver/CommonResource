package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.ApiResponse
import com.example.myapplication.network.RetrofitService
import com.example.myapplication.utils.Utilities
import kotlinx.coroutines.launch
import network.ApiInterface

class ProductsViewModel(): ViewModel() {

    var listCustomer = MutableLiveData<List<Any>>()
    private var apiInterface: ApiInterface? = null

    init {
        callApiMethod()
    }

    private fun callApiMethod() {
        apiInterface = RetrofitService.getClient()?.create(ApiInterface::class.java)

        viewModelScope.launch {

            val result = apiInterface?.getProducts()
            if (result != null) {
                if(result.isSuccessful){

                    Utilities.logE("--res--"+result.body())

                }
            }

            //Working
            ApiResponse.loading(apiInterface?.getProducts() == null)
            try {
                ApiResponse.success(apiInterface?.getProducts())
            }catch (exception: Exception) {
                ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!")
            }


        }
    }

}