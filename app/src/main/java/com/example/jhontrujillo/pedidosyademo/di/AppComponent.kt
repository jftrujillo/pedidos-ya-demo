package com.example.jhontrujillo.pedidosyademo.di

import com.example.jhontrujillo.pedidosyademo.MainActivity
import com.example.jhontrujillo.pedidosyademo.ui.landingScreen.view.LandingScreenActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules =  [AppModule::class , LandingScreenModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(target : LandingScreenActivity)
}