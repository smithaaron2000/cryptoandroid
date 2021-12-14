package org.wit.cryptocurrency.main

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
        //cryptos = getFirestore()

        i("Cryptocurrency started")
    }

//    private fun getFirestore(): CryptocurrencyStore {
//        firestore.collection("cryptos")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    i("${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { e ->
//                i(e, "Error getting documents: ")
//            }
//        return cryptos
//    }
}