package org.wit.cryptocurrency.main

import android.app.Application
import org.wit.cryptocurrency.models.CryptocurrencyJSONStore
import org.wit.cryptocurrency.models.CryptocurrencyStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var cryptos : CryptocurrencyStore
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        cryptos = CryptocurrencyJSONStore(applicationContext)
        i("Cryptocurrency started")
    }
}