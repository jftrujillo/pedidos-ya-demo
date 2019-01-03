package com.example.jhontrujillo.pedidosyademo.di

import android.content.Context
import com.example.jhontrujillo.pedidosyademo.net.ApiClient
import com.example.jhontrujillo.pedidosyademo.ui.landingScreen.presenter.LandingScreenPresenter
import dagger.Module
import dagger.Provides


@Module
class LandingScreenModule {

    @Provides
    fun provideLandingScreenPresenter(apiClient: ApiClient, context: Context) = LandingScreenPresenter(apiClient = apiClient, context = context)

}