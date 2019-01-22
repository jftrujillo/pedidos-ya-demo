package com.example.jhontrujillo.pedidosyademo.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.example.jhontrujillo.pedidosyademo.net.apiClient.TokenClient
import com.example.jhontrujillo.pedidosyademo.util.BASE_URL
import com.example.jhontrujillo.pedidosyademo.util.SHARED_PREFERENCE_NAME
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {


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
    fun provideTokenClient(retrofit: Retrofit) : TokenClient = retrofit.create(TokenClient::class.java)

    @Provides
    @Singleton
    fun provideSharedPrefferences(context: Context) = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0)

    @Provides
    @Singleton
    fun provideLocationManager (context: Context) : LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
}