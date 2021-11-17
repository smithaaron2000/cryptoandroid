package org.wit.cryptocurrency.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.cryptocurrency.databinding.ActivityCryptocurrencyBinding
import org.wit.cryptocurrency.models.CryptocurrencyModel
import timber.log.Timber
import timber.log.Timber.i

class CryptocurrencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCryptocurrencyBinding
    var crypto = CryptocurrencyModel()
    val cryptos = ArrayList<CryptocurrencyModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCryptocurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("Cryptocurrency Activity started..")

        binding.btnAdd.setOnClickListener() {
            crypto.name = binding.cryptoName.text.toString()
            crypto.symbol = binding.cryptoSymbol.text.toString()
            crypto.initial_price_usd = binding.cryptoInitialPriceUSD.text.toString().toDouble()
            crypto.amount_invested_usd = binding.cryptoAmountInvestedUSD.text.toString().toDouble()
            crypto.current_price_usd = binding.cryptoCurrentPriceUSD.text.toString().toDouble()
            if (crypto.name.isNotEmpty()) {
                cryptos.add(crypto.copy())
                i("add Button Pressed: ${crypto}")
                for (i in cryptos.indices)
                { i("Cryptocurrency[$i]:${this.cryptos[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a Name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}