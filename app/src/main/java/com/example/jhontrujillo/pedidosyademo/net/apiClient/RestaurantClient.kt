package com.example.jhontrujillo.pedidosyademo.net.apiClient

import com.example.jhontrujillo.pedidosyademo.model.Response
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantClient {
    @GET("search/restaurants")
    fun getRestaurants(@Query("point") point: String,
                       @Query("country") country: Int,
                       @Query("max") amountOfElements: Int): Observable<Response>

    @GET("search/restaurants")
    fun getRestaurantsWithOffset(@Query("point") point: String,
                                 @Query("country") country: Int,
                                 @Query("max") amountOfElements: Int,
                                 @Query("offset") offset: Int): Single<Response>
}