package com.example.jhontrujillo.pedidosyademo

import android.app.Application
import com.example.jhontrujillo.pedidosyademo.di.AppComponent
import com.example.jhontrujillo.pedidosyademo.di.AppModule
import com.example.jhontrujillo.pedidosyademo.di.DaggerAppComponent


class App : Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = createAppComponent()

    }


    private fun createAppComponent(): AppComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()


}