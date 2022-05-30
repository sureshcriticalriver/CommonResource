package com.example.myapplication.network

import com.example.myapplication.model.Products
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

object RetrofitService {

        private const val BASE_URL = "https://reqres.in/"//posts

         var retrofit: Retrofit? = null
         fun getClient(): Retrofit? {
             if (retrofit == null) {

                 val logging = HttpLoggingInterceptor()
                 logging.setLevel(HttpLoggingInterceptor.Level.BODY)

                 val httpClient = OkHttpClient.Builder()
                     .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                     .readTimeout(5, TimeUnit.MINUTES)
                     .connectTimeout(5, TimeUnit.MINUTES)
                 httpClient.addInterceptor(logging)  // <-- this is the important line!
                 retrofit = Retrofit.Builder()
                     .baseUrl(BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .client(httpClient.build())
                     .build()


             }
             return retrofit
         }


    }