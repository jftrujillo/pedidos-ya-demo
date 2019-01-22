package com.example.jhontrujillo.pedidosyademo.ui.listOfRestaurants

import android.location.Location
import com.example.jhontrujillo.pedidosyademo.model.Restaurant
import com.example.jhontrujillo.pedidosyademo.ui.BasePresenter
import com.example.jhontrujillo.pedidosyademo.ui.BaseView
import com.google.android.gms.maps.model.LatLng


interface ListOfRestaurantsMVP {

    interface View : BaseView {
        fun updateListOfRestaurants(listOfRestaurants : List<Restaurant>)
        fun addRestaurantMarker(restaurant: Restaurant)
        fun cleanMarkers(userPoint : LatLng)
        fun showUpdantingRestaurantsProgress()
        fun hideUpdantingRestaurantsProgress()
        fun showAddingRestaurantsProgress()
        fun hideAddingRestaurantsProgress()
        fun removeLocationUpdates()
    }

    interface Presenter: BasePresenter{
        fun addNewRestaurantsToCurrentList()
        fun updatePosition(point : LatLng)
        fun onCurrentPositionRetrieveIt(point: Location)
        fun getCurrentPoint() : LatLng
    }
}