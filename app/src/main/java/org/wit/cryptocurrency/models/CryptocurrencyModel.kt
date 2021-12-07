package org.wit.cryptocurrency.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptocurrencyModel(
    var id: Long = 0,
    var name: String = "",
    var symbol: String = "",
    var initial_price_usd: Double = 0.0,
    var amount_invested_usd: Double = 0.0,
    var num_shares: Double = amount_invested_usd / initial_price_usd,
    var current_price_usd: Double = 0.0,
    var investment_value: Double = num_shares * current_price_usd,
    var return_on_investment: Double = investment_value - amount_invested_usd,
    var image: Uri = Uri.EMPTY,
    var lat : Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable