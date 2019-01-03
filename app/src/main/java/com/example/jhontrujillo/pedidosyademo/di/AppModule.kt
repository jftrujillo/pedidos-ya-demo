package com.example.jhontrujillo.pedidosyademo.di

import android.app.Application
import android.content.Context
import com.example.jhontrujillo.pedidosyademo.net.ApiClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {

    companion object {
        const val BASE_URL = "http://stg-api.pedidosya.com/public/v1/"
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    @Singleton
    @Provides
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit) : ApiClient = retrofit.create(ApiClient::class.java)
}