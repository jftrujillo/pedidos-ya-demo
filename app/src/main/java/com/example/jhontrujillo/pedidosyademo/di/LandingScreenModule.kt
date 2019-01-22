package com.example.jhontrujillo.pedidosyademo.di

import android.content.Context
import com.example.jhontrujillo.pedidosyademo.net.apiClient.TokenClient
import com.example.jhontrujillo.pedidosyademo.ui.landingScreen.presenter.LandingScreenPresenter
import dagger.Module
import dagger.Provides


@Module
class LandingScreenModule {

    @Provides
    fun provideLandingScreenPresenter(tokenClient: TokenClient, context: Context) = LandingScreenPresenter(tokenClient = tokenClient, context = context)

}