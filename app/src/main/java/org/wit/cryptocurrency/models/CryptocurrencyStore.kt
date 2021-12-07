package org.wit.cryptocurrency.models

interface CryptocurrencyStore {
    fun findAll(): List<CryptocurrencyModel>
    fun create(crypto: CryptocurrencyModel)
    fun update(crypto: CryptocurrencyModel)
    fun delete(crypto: CryptocurrencyModel)

}