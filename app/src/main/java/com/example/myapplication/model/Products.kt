package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("support")
    val support: Support = Support()
) {
    data class Data(
        @SerializedName("color")
        val color: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("pantone_value")
        val pantoneValue: String = "",
        @SerializedName("year")
        val year: Int = 0
    )

    data class Support(
        @SerializedName("text")
        val text: String = "",
        @SerializedName("url")
        val url: String = ""
    )
}