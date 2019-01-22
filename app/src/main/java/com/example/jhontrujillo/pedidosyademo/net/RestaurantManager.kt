package com.example.jhontrujillo.pedidosyademo.net

import com.example.jhontrujillo.pedidosyademo.model.Response
import com.example.jhontrujillo.pedidosyademo.model.Restaurant
import com.example.jhontrujillo.pedidosyademo.net.apiClient.RestaurantClient

interface RestaurantManager {
    fun getListOfRestaurantsByResponse(response: Response): List<Restaurant>
    fun getRestaurantListClient(accessToken: String): RestaurantClient
    var currentPoint: String
}