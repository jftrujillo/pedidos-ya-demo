package com.example.jhontrujillo.pedidosyademo.model

data class Restaurant(
        val latitude : Double,
        val longitude: Double,
        val name : String,
        val ratingScore : String,
        val paymentMethods: String,
        val deliveryTimeMinMinutes : String,
        val categories : String,
        val deliveryAreas : String
)