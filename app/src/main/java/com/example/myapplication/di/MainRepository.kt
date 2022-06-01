package com.example.myapplication.di

import network.ApiInterface
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getAllProducts() = apiInterface.getProducts()

}