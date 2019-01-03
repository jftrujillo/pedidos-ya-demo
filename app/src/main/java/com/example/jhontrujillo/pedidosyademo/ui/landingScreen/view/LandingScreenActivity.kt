package com.example.jhontrujillo.pedidosyademo.ui.landingScreen.view


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.jhontrujillo.pedidosyademo.App
import com.example.jhontrujillo.pedidosyademo.MainActivity
import com.example.jhontrujillo.pedidosyademo.R
import com.example.jhontrujillo.pedidosyademo.R.id.progressBar
import com.example.jhontrujillo.pedidosyademo.ui.landingScreen.LandingScreenMVP
import com.example.jhontrujillo.pedidosyademo.ui.landingScreen.presenter.LandingScreenPresenter
import kotlinx.android.synthetic.main.landing_screen_view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class LandingScreenActivity : AppCompatActivity(), LandingScreenMVP.View{

    @Inject
    lateinit var presenter : LandingScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_screen_view)
        injectDepedencies(this)
        presenter.onViewCreated(this)
    }


    private fun injectDepedencies(landingScreenActivity: LandingScreenActivity) {
        (application as App).appComponent.inject(landingScreenActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }
    override fun startViewProgress() {
        progressBar.isIndeterminate = true
    }

    override fun stopViewProgress() {
        progressBar.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun showErrorBanner() {
        parentPanel.snackbar(getString(R.string.error_token))
        progressBar.visibility = View.GONE
    }


}