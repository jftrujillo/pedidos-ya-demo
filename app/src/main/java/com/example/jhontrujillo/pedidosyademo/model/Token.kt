package com.example.jhontrujillo.pedidosyademo.model

import com.google.gson.annotations.SerializedName

data class Token (
        @SerializedName("access_token")
        val accessToken: String
)