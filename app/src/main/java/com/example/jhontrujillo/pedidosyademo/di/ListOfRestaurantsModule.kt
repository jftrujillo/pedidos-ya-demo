package com.example.jhontrujillo.pedidosyademo.di

import android.content.SharedPreferences
import com.example.jhontrujillo.pedidosyademo.net.RestaurantManager
import com.example.jhontrujillo.pedidosyademo.net.managerImp.RestaurantsManagerImp
import com.example.jhontrujillo.pedidosyademo.ui.listOfRestaurants.ListOfRestaurantsMVP
import com.example.jhontrujillo.pedidosyademo.ui.listOfRestaurants.presenter.ListOfRestaurantsPresenterImp
import dagger.Module
import dagger.Provides

@Module
class ListOfRestaurantsModule {

    @Provides
    fun provideRestaurantManager() : RestaurantManager = RestaurantsManagerImp()

    @Provides
    fun provideListOfRestaurantPresenter(restaurantsManager: RestaurantManager, sharedPreferences: SharedPreferences): ListOfRestaurantsMVP.Presenter {
        return ListOfRestaurantsPresenterImp(
                restaurantsManager = restaurantsManager,
                sharedPreferences = sharedPreferences)
    }
}
