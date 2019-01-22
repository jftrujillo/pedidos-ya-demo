package com.example.jhontrujillo.pedidosyademo.ui.landingScreen.view


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.jhontrujillo.pedidosyademo.App
import com.example.jhontrujillo.pedidosyademo.R
import com.example.jhontrujillo.pedidosyademo.R.id.progressBar
import com.example.jhontrujillo.pedidosyademo.ui.landingScreen.LandingScreenMVP
import com.example.jhontrujillo.pedidosyademo.ui.landingScreen.presenter.LandingScreenPresenter
import com.example.jhontrujillo.pedidosyademo.ui.listOfRestaurants.view.ListOfRestaurantsActivity
import kotlinx.android.synthetic.main.landing_screen_view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject

class LandingScreenActivity : AppCompatActivity(), LandingScreenMVP.View {


    @Inject
    lateinit var presenter: LandingScreenPresenter

    private val FINE_POSITION_REQUEST = 100

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
        if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), FINE_POSITION_REQUEST)
        } else {
            startActivity(Intent(this, ListOfRestaurantsActivity::class.java))
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            FINE_POSITION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent(this, ListOfRestaurantsActivity::class.java))
                    finish()
                } else {
                    alert {
                        message = "If you don't grand acces permision, you will be not able to use the app"
                        positiveButton("Okay", {
                            finish()
                        })
                    }
                }
            }

        }
    }


    override fun showErrorBanner(message: String) {
        parentPanel.snackbar(getString(R.string.error_token))
        progressBar.visibility = View.GONE
    }


}