package com.example.jhontrujillo.pedidosyademo.ui.listOfRestaurants.view

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.jhontrujillo.pedidosyademo.App
import com.example.jhontrujillo.pedidosyademo.R
import com.example.jhontrujillo.pedidosyademo.R.id.*
import com.example.jhontrujillo.pedidosyademo.model.Restaurant
import com.example.jhontrujillo.pedidosyademo.ui.listOfRestaurants.ListOfRestaurantsMVP
import com.example.jhontrujillo.pedidosyademo.ui.listOfRestaurants.adapter.ListOfRestaurantAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.list_of_resturants_view.*
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject
import com.example.jhontrujillo.pedidosyademo.util.getBitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker


class ListOfRestaurantsActivity : AppCompatActivity(), OnMapReadyCallback, ListOfRestaurantsMVP.View {


    @Inject
    lateinit var presenter: ListOfRestaurantsMVP.Presenter

    @Inject
    lateinit var locationManager: LocationManager

    var enableScrollListener = true

    private lateinit var mMap: GoogleMap

    private lateinit var userMarker: Marker

    private var listOfRestaurantAdapter: ListOfRestaurantAdapter? = null

    private lateinit var locationListener: LocationListener

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_of_resturants_view)
        injectDependencies(this)
        presenter.onViewCreated(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        locationListener = object : LocationListener {
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

            override fun onProviderEnabled(provider: String?) {}

            override fun onProviderDisabled(provider: String?) {}

            override fun onLocationChanged(location: Location?) {
                if (location != null) {
                    presenter.onCurrentPositionRetrieveIt(location)
                    mapFragment.getMapAsync(this@ListOfRestaurantsActivity)
                }
            }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
        locationManager.removeUpdates(locationListener)

    }

    override fun updateListOfRestaurants(listOfRestaurants: List<Restaurant>) {
        if (listOfRestaurantAdapter == null) {
            listOfRestaurantAdapter = ListOfRestaurantAdapter(listOfRestaurants)
            list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (recyclerView?.canScrollVertically(1)?.not()!!) {
                        if (enableScrollListener) {
                            enableScrollListener = false
                            presenter.addNewRestaurantsToCurrentList()
                        }
                    }
                }
            })
            list.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = listOfRestaurantAdapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
            return
        }
        listOfRestaurantAdapter?.notifyDataSetChanged()
        enableScrollListener = true
   }

    override fun addRestaurantMarker(restaurant: Restaurant) {
        mMap.addMarker(MarkerOptions()
                .title(restaurant.name)
                .position(LatLng(restaurant.latitude, restaurant.longitude))
                .icon(BitmapDescriptorFactory.defaultMarker()))
    }

    override fun cleanMarkers(userPoint: LatLng) {
        mMap.clear()
        userMarker = mMap.addMarker(MarkerOptions()
                .position(userPoint)
                .title("You are here")
                .icon(getBitmapDescriptor(R.drawable.ic_face_black_24dp)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userPoint))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f))

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        userMarker = mMap.addMarker(MarkerOptions()
                .position(presenter.getCurrentPoint())
                .title("You are here")
                .icon(getBitmapDescriptor(R.drawable.ic_face_black_24dp)))
        mMap.setOnMapClickListener { point ->
            updateUserPosition(point)
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(presenter.getCurrentPoint()))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
    }


    override fun showErrorBanner(message: String) {
        parentPanel.snackbar("Error retrieving list of restaurants")
    }


    override fun removeLocationUpdates() {
        locationManager.removeUpdates(locationListener)
    }

    private fun updateUserPosition(point: LatLng) {
        presenter.updatePosition(point)
        mMap.clear()
        userMarker = mMap.addMarker(MarkerOptions()
                .position(point)
                .title("You are here")
                .icon(getBitmapDescriptor(R.drawable.ic_face_black_24dp)))

    }

    override fun showUpdantingRestaurantsProgress() {
        list.visibility = View.GONE
        updating_restaurant_progress.visibility = View.VISIBLE
    }

    override fun hideUpdantingRestaurantsProgress() {
        list.visibility = View.VISIBLE
        updating_restaurant_progress.visibility = View.GONE
    }

    override fun showAddingRestaurantsProgress() {
        add_restaurants_progress.visibility = View.VISIBLE
    }

    override fun hideAddingRestaurantsProgress() {
        add_restaurants_progress.visibility = View.GONE
    }


    private fun injectDependencies(listOfRestaurantsActivity: ListOfRestaurantsActivity) {
        (application as App).appComponent.inject(listOfRestaurantsActivity)
    }
}