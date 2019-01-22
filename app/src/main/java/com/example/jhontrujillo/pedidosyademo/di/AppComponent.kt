package com.example.jhontrujillo.pedidosyademo.di

import com.example.jhontrujillo.pedidosyademo.MainActivity
import com.example.jhontrujillo.pedidosyademo.ui.landingScreen.view.LandingScreenActivity
import com.example.jhontrujillo.pedidosyademo.ui.listOfRestaurants.view.ListOfRestaurantsActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules =  [AppModule::class , LandingScreenModule::class, ListOfRestaurantsModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(target : LandingScreenActivity)

    fun inject(target : ListOfRestaurantsActivity)

}