package com.common.resource.di

import com.common.resource.network.ApiInterface
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getAllProducts() = apiInterface.getProducts()

}