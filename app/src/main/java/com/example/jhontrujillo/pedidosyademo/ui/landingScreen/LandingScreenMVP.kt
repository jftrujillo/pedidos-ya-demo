package com.example.jhontrujillo.pedidosyademo.ui.landingScreen

import com.example.jhontrujillo.pedidosyademo.ui.BasePresenter
import com.example.jhontrujillo.pedidosyademo.ui.BaseView

interface LandingScreenMVP {

    interface View : BaseView {
        fun startViewProgress()
        fun stopViewProgress()
    }

    interface Presenter : BasePresenter
}