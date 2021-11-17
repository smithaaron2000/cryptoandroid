package org.wit.cryptocurrency.main

import android.app.Application
import org.wit.cryptocurrency.models.CryptocurrencyModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val cryptos = ArrayList<CryptocurrencyModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Cryptocurrency started")
    }
}