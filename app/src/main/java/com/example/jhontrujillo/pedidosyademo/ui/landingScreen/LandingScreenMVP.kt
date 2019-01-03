package com.example.jhontrujillo.pedidosyademo.ui.landingScreen

import com.example.jhontrujillo.pedidosyademo.ui.BasePresenter

interface LandingScreenMVP {

    interface View : BaseView{
        fun startViewProgress()
        fun stopViewProgress()
        fun showErrorBanner()
    }

    interface Presenter : BasePresenter
}