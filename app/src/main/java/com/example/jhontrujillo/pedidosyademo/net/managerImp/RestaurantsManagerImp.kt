package com.example.jhontrujillo.pedidosyademo.net.managerImp

import com.example.jhontrujillo.pedidosyademo.model.Response
import com.example.jhontrujillo.pedidosyademo.model.Restaurant
import com.example.jhontrujillo.pedidosyademo.net.RestaurantManager
import com.example.jhontrujillo.pedidosyademo.net.apiClient.RestaurantClient
import com.example.jhontrujillo.pedidosyademo.util.BASE_URL
import com.example.jhontrujillo.pedidosyademo.util.DEFAULT_POINT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsManagerImp : RestaurantManager {

    override var currentPoint: String
        get() = DEFAULT_POINT
        set(value) {}

    override fun getListOfRestaurantsByResponse(response: Response): List<Restaurant> {
        return response.restaurants.map {
            val point = it.point.split(",")
            Restaurant(
                    latitude = point[0].toDouble(),
                    longitude = point[1].toDouble(),
                    ratingScore = it.ratingScore,
                    paymentMethods = it.paymentMethods,
                    deliveryTimeMinMinutes = it.deliveryTimeMinMinutes,
                    categories = it.categories,
                    deliveryAreas = it.deliveryAreas,
                    name = it.name
            )
        }
    }
    override fun getRestaurantListClient(accessToken: String): RestaurantClient {
        val httpClient = OkHttpClient
                .Builder()
                .addInterceptor {
                    val requestBuilder = it.request()
                            .newBuilder()
                            .header("Authorization", accessToken)
                    val request = requestBuilder.build()
                    it.proceed(request)
                }.build()

        val retrofitClient =  Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build()
        return retrofitClient.create(RestaurantClient::class.java)
    }
}