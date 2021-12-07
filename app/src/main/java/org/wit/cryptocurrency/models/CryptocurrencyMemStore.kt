package org.wit.cryptocurrency.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class CryptocurrencyMemStore : CryptocurrencyStore {

    val cryptos = ArrayList<CryptocurrencyModel>()

    override fun findAll(): List<CryptocurrencyModel> {
        return cryptos
    }

    override fun create(crypto: CryptocurrencyModel) {
        cryptos.add(crypto)
        logAll()
    }
    override fun update(crypto: CryptocurrencyModel) {
        var foundCrypto: CryptocurrencyModel? = cryptos.find { c -> c.id == crypto.id }
        if (foundCrypto != null) {
            foundCrypto.name = crypto.name
            foundCrypto.symbol = crypto.symbol
            foundCrypto.initial_price_usd = crypto.initial_price_usd
            foundCrypto.amount_invested_usd = crypto.amount_invested_usd
            foundCrypto.num_shares = crypto.num_shares
            foundCrypto.current_price_usd = crypto.current_price_usd
            foundCrypto.investment_value = crypto.num_shares * crypto.current_price_usd
            foundCrypto.return_on_investment = crypto.investment_value - crypto.amount_invested_usd
            foundCrypto.image = crypto.image
            foundCrypto.lat = crypto.lat
            foundCrypto.lng = crypto.lng
            foundCrypto.zoom = crypto.zoom
            logAll()
        }
    }

    override fun delete(crypto: CryptocurrencyModel) {
        cryptos.remove(crypto)
    }


    fun logAll() {
        cryptos.forEach{ i("${it}") }
    }

}