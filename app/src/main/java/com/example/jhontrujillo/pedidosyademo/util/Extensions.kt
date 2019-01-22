package com.example.jhontrujillo.pedidosyademo.util

import android.content.Context
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.location.Location
import android.os.Build



fun String.formatPoint() : LatLng = LatLng(this.split(",")[0].toDouble(), this.split(",")[1].toDouble())

fun LatLng.formatPoint() : String = StringBuilder().append(this.latitude).append(",").append(this.longitude).toString()

fun Location.formatPoint(): String = StringBuilder().append(this.latitude.toString()).append(",").append(this.longitude.toString()).toString()

fun Context.getBitmapDescriptor(resource: Int) : BitmapDescriptor {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val vectorDrawable = this.getDrawable(resource) as VectorDrawable

        val h = vectorDrawable.intrinsicHeight
        val w = vectorDrawable.intrinsicWidth

        vectorDrawable.setBounds(0, 0, w, h)

        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bm)

    } else {
        return BitmapDescriptorFactory.fromResource(resource)
    }
}