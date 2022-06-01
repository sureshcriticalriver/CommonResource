package com.common.resource.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.common.resource.di.MainRepository
import com.common.resource.network.NetworkHelper
import com.common.resource.utils.Utilities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val mainRepository: MainRepository,
                                            private val networkHelper: NetworkHelper
): ViewModel() {

    /*You make a ViewModel with LiveData (internal) and MutableLiveData (external)
    Now you can use the data directly in views, fragments or activities*/
    private val _status = MutableLiveData<Any>()

    init {
        callApi()
    }

    private fun callApi() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                //val result = mainRepository.getAllProducts()
                try {
                    val response = mainRepository.getAllProducts()
                    if(response.code() == 200 || response.code() == 201){
                        Utilities.logV("Res---"+response.body())

                        _status.value = response.body()
                    }else {
                        _status.value = response.errorBody()

                    }
                } catch (ioe: IOException) {
                    Utilities.logE("IOException "+ioe.message)
                } catch (he: HttpException) {
                    Utilities.logE("HttpException "+he.message())

                }
                Utilities.logV("get data =="+_status.value)


            }else{
                Utilities.logE("No Network")
            }

        }
    }
}
