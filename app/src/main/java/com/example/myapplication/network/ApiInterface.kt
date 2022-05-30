package network

import androidx.lifecycle.LiveData
import com.example.myapplication.model.Products
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface ApiInterface {

    @GET("api/products/3")
    suspend fun getProducts() :  Response<Products>
    
}