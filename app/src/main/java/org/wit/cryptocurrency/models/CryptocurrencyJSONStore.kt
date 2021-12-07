package org.wit.cryptocurrency.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.cryptocurrency.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "cryptos.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<CryptocurrencyModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class CryptocurrencyJSONStore(private val context: Context) : CryptocurrencyStore {

    var cryptos = mutableListOf<CryptocurrencyModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<CryptocurrencyModel> {
        logAll()
        return cryptos
    }

    override fun create(crypto: CryptocurrencyModel) {
        crypto.id = generateRandomId()
        crypto.num_shares = crypto.amount_invested_usd / crypto.initial_price_usd
        crypto.investment_value = crypto.num_shares * crypto.current_price_usd
        crypto.return_on_investment = crypto.investment_value - crypto.amount_invested_usd
        cryptos.add(crypto)
        serialize()
    }

    override fun update(crypto: CryptocurrencyModel) {
        val cryptosList = findAll() as ArrayList<CryptocurrencyModel>
        var foundCrypto: CryptocurrencyModel? = cryptosList.find { c -> c.id == crypto.id }
        if (foundCrypto != null) {
            foundCrypto.name = crypto.name
            foundCrypto.symbol = crypto.symbol
            foundCrypto.initial_price_usd = crypto.initial_price_usd
            foundCrypto.amount_invested_usd = crypto.amount_invested_usd
            foundCrypto.num_shares = crypto.num_shares
            foundCrypto.current_price_usd = crypto.current_price_usd
            foundCrypto.investment_value = crypto.num_shares * crypto.current_price_usd
            foundCrypto.return_on_investment = foundCrypto.investment_value - crypto.amount_invested_usd
            foundCrypto.image = crypto.image
            foundCrypto.lat = crypto.lat
            foundCrypto.lng = crypto.lng
            foundCrypto.zoom = crypto.zoom
        }
        serialize()
    }

    override fun delete(crypto: CryptocurrencyModel) {
        cryptos.remove(crypto)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(cryptos, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        cryptos = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        cryptos.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}