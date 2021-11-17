package org.wit.cryptocurrency.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.cryptocurrency.R
import org.wit.cryptocurrency.databinding.ActivityCryptocurrencyBinding
import org.wit.cryptocurrency.main.MainApp
import org.wit.cryptocurrency.models.CryptocurrencyModel
import timber.log.Timber
import timber.log.Timber.i

class CryptocurrencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCryptocurrencyBinding
    var crypto = CryptocurrencyModel()
    lateinit var app : MainApp
    //val cryptos = ArrayList<CryptocurrencyModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCryptocurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Cryptocurrency Activity Started...")
        binding.btnAdd.setOnClickListener() {
            crypto.name = binding.cryptoName.text.toString()
            crypto.symbol = binding.cryptoSymbol.text.toString()
            //crypto.initial_price_usd = binding.cryptoInitialPriceUSD.text.toString().toDouble()
            //crypto.amount_invested_usd = binding.cryptoAmountInvestedUSD.text.toString().toDouble()
            //crypto.current_price_usd = binding.cryptoCurrentPriceUSD.text.toString().toDouble()
            if (crypto.name.isNotEmpty()) {
                app.cryptos.add(crypto.copy())
                i("add Button Pressed: ${crypto}")
                for (i in app.cryptos.indices)
                {
                    i("Cryptocurrency[$i]:${this.app.cryptos[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a Name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cryptocurrency, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}