package com.example.jhontrujillo.pedidosyademo.net.apiClient

import com.example.jhontrujillo.pedidosyademo.model.Response
import com.example.jhontrujillo.pedidosyademo.model.Token
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TokenClient {

    @GET("tokens")
    fun getToken(@Query("clientId") clientId: String,
                 @Query("clientSecret") clientSecret: String) : Observable<Token>
}