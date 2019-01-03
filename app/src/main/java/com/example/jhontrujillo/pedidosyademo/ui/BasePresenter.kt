package com.example.jhontrujillo.pedidosyademo.ui

import com.example.jhontrujillo.pedidosyademo.ui.landingScreen.BaseView

interface BasePresenter {

    fun onViewCreated(view : BaseView)
    fun onViewDestroyed()
}