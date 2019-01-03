package com.example.jhontrujillo.pedidosyademo.net

import com.example.jhontrujillo.pedidosyademo.model.Token
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("tokens")
    fun getToken(@Query("clientId") clientId: String,
                 @Query("clientSecret") clientSecret: String) : Observable<Token>
}