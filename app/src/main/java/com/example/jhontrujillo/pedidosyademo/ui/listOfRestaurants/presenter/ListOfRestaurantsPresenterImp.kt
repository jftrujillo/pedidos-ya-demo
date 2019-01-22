package com.example.jhontrujillo.pedidosyademo.ui.listOfRestaurants.presenter

import android.content.SharedPreferences
import android.location.Location
import android.util.Log
import com.example.jhontrujillo.pedidosyademo.model.Restaurant
import com.example.jhontrujillo.pedidosyademo.net.RestaurantManager
import com.example.jhontrujillo.pedidosyademo.ui.BaseView
import com.example.jhontrujillo.pedidosyademo.ui.listOfRestaurants.ListOfRestaurantsMVP
import com.example.jhontrujillo.pedidosyademo.util.TOKEN_PREFERENCE_KEY
import com.example.jhontrujillo.pedidosyademo.util.formatPoint
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ListOfRestaurantsPresenterImp @Inject constructor(val restaurantsManager: RestaurantManager, val sharedPreferences: SharedPreferences) :
        ListOfRestaurantsMVP.Presenter {


    private var subscription: Disposable? = null
    private lateinit var view: ListOfRestaurantsMVP.View
    private var listOfRestaurants: MutableList<Restaurant> = mutableListOf()


    private fun loadNewRestaurants() {
        view.showUpdantingRestaurantsProgress()
        subscription = restaurantsManager
                .getRestaurantListClient(sharedPreferences.getString(TOKEN_PREFERENCE_KEY, ""))
                .getRestaurants(restaurantsManager.currentPoint, 1, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listOfRestaurants.clear()
                    listOfRestaurants.addAll(restaurantsManager.getListOfRestaurantsByResponse(it))
                    view.updateListOfRestaurants(listOfRestaurants)
                    view.cleanMarkers(restaurantsManager.currentPoint.formatPoint())
                    listOfRestaurants.forEach { view.addRestaurantMarker(it) }
                    view.hideUpdantingRestaurantsProgress()
                }, {
                    Log.e("Error", it.message)
                    view.showErrorBanner(it.message ?: "Error getting list of restaurants")
                })
    }


    override fun addNewRestaurantsToCurrentList() {
        if (listOfRestaurants.size < 40) {
            view.showAddingRestaurantsProgress()
            subscription = restaurantsManager
                    .getRestaurantListClient(sharedPreferences.getString(TOKEN_PREFERENCE_KEY, ""))
                    .getRestaurantsWithOffset(restaurantsManager.currentPoint, 1, 20, 20)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val additionalRestaurants = restaurantsManager.getListOfRestaurantsByResponse(it)
                        listOfRestaurants.addAll(additionalRestaurants)
                        view.updateListOfRestaurants(listOfRestaurants)
                        additionalRestaurants.forEach { view.addRestaurantMarker(it) }
                        view.hideAddingRestaurantsProgress()
                    }, {
                        Log.e("Error", it.message)
                        view.showErrorBanner(it.message ?: "Error getting list of restaurants")
                    })
        }
    }

    override fun onViewCreated(view: BaseView) {
        this.view = view as ListOfRestaurantsMVP.View
        view.showUpdantingRestaurantsProgress()
    }

    override fun onCurrentPositionRetrieveIt(point: Location) {
        restaurantsManager.currentPoint = point.formatPoint()
        loadNewRestaurants()
        view.removeLocationUpdates()
    }

    override fun updatePosition(point: LatLng) {
        restaurantsManager.currentPoint = point.formatPoint()
        loadNewRestaurants()

    }

    override fun getCurrentPoint(): LatLng = restaurantsManager.currentPoint.formatPoint()


    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}


