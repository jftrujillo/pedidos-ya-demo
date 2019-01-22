package com.example.jhontrujillo.pedidosyademo.ui.listOfRestaurants.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jhontrujillo.pedidosyademo.R
import com.example.jhontrujillo.pedidosyademo.model.Restaurant

class ListOfRestaurantAdapter(private val restaurants : List<Restaurant>) : RecyclerView.Adapter<ListOfRestaurantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_restaurants_template, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = restaurants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRestaurant = restaurants[position]
        holder.let {
            it.name?.text =  currentRestaurant.name
            it.categories?.text = currentRestaurant.categories
            it.ratingScore?.text = currentRestaurant.ratingScore
            it.deliveryTimeMinMinutes?.text = currentRestaurant.deliveryTimeMinMinutes
            it.positionLabel?.text = position.toString()
        }
    }


    class ViewHolder (view : View?) : RecyclerView.ViewHolder(view) {
        var name: TextView? = view?.findViewById(R.id.name)
        var categories : TextView? = view?.findViewById(R.id.categories)
        var ratingScore : TextView? = view?.findViewById(R.id.ratingScore)
        var deliveryTimeMinMinutes: TextView? = view?.findViewById(R.id.deliveryTimeMinMinutes)
        var positionLabel : TextView? = view?.findViewById(R.id.position)
    }
}