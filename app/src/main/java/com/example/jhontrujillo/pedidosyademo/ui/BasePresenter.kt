package com.example.jhontrujillo.pedidosyademo.ui

interface BasePresenter {

    fun onViewCreated(view : BaseView)
    fun onViewDestroyed()
}