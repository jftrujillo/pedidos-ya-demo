package com.example.jhontrujillo.pedidosyademo.model

import com.google.gson.annotations.SerializedName

data class Response (
        @SerializedName("data")
        val restaurants : List<RestaurantResponse>
) {
    data class RestaurantResponse(
            @SerializedName("coordinates")
            val point : String,
            @SerializedName("ratingScore")
            val ratingScore : String,
            @SerializedName("paymentMethods")
            val paymentMethods: String,
            @SerializedName("deliveryTimeMinMinutes")
            val deliveryTimeMinMinutes : String,
            @SerializedName("allCategories")
            val categories : String,
            @SerializedName("deliveryAreas")
            val deliveryAreas : String,
            @SerializedName("name")
            val name: String
    )
}
