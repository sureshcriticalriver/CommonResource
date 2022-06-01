package network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET


interface ApiInterface {

    @GET("api/products/3")
    suspend fun getProducts() :  Response<JsonObject>
    
}