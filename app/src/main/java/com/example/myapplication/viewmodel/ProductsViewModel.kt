package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.di.MainRepository
import com.example.myapplication.network.NetworkHelper
import com.example.myapplication.network.RetrofitService
import com.example.myapplication.utils.Utilities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import network.ApiInterface
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val mainRepository: MainRepository,
                                            private val networkHelper: NetworkHelper
): ViewModel() {

    var listCustomer = MutableLiveData<List<Any>>()

    init {
        callApiMethod()
    }

    private fun callApiMethod() {

        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()){
                val result = mainRepository.getAllProducts()
                if (result != null) {
                    if(result.code() == 200) Utilities.logE("--res 11--"+result.body())
                }else Utilities.logE("Error Occurred!")
            }


            //Working
            /*ApiResponse.loading(apiInterface?.getProducts() == null)
            try {
                ApiResponse.success(apiInterface?.getProducts())
            }catch (exception: Exception) {
                ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!")
            }*/


        }
    }

}