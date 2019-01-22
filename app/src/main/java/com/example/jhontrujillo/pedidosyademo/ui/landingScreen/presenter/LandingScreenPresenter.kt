package com.example.jhontrujillo.pedidosyademo.ui.landingScreen.presenter

import android.content.Context
import android.util.Log
import com.example.jhontrujillo.pedidosyademo.net.apiClient.TokenClient
import com.example.jhontrujillo.pedidosyademo.ui.BaseView
import com.example.jhontrujillo.pedidosyademo.ui.landingScreen.LandingScreenMVP
import com.example.jhontrujillo.pedidosyademo.util.SHARED_PREFERENCE_NAME
import com.example.jhontrujillo.pedidosyademo.util.TOKEN_PREFERENCE_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LandingScreenPresenter @Inject constructor(val tokenClient: TokenClient, val context: Context) : LandingScreenMVP.Presenter {
    companion object {
        const val clientId = "trivia_f"
        const val clientSecret = "PeY@@Tr1v1@943"
    }

    private lateinit var view: LandingScreenMVP.View


    private var subscription: Disposable? = null

    override fun onViewCreated(view: BaseView) {
        this.view = view as LandingScreenMVP.View
        getAccessToken(clientId = clientId, clientSecret = clientSecret)
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }


    private fun getAccessToken(clientId: String, clientSecret: String) {
        subscription = tokenClient.getToken(clientId = clientId, clientSecret = clientSecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                {
                    token ->
                    val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0)
                    sharedPreferences.edit().putString(TOKEN_PREFERENCE_KEY, token.accessToken).apply()
                    view.stopViewProgress()

                },
                {error ->
                    Log.e("error", error.message)
                    view.showErrorBanner(error.message ?: "Error getting token")
                }
        )
    }
}
