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
        var edit = false

        binding = ActivityCryptocurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("cryptocurrency_edit")) {
            edit = true
            crypto = intent.extras?.getParcelable("cryptocurrency_edit")!!
            binding.cryptoName.setText(crypto.name)
            binding.cryptoSymbol.setText(crypto.symbol)
            binding.cryptoInitialPriceUSD.setText(crypto.initial_price_usd.toString())
            binding.cryptoAmountInvestedUSD.setText(crypto.amount_invested_usd.toString())
            binding.cryptoCurrentPriceUSD.setText(crypto.current_price_usd.toString())
            binding.btnAdd.setText(R.string.save_crypto)
        }
        binding.btnAdd.setOnClickListener() {
            crypto.name = binding.cryptoName.text.toString()
            crypto.symbol = binding.cryptoSymbol.text.toString()
            crypto.initial_price_usd = binding.cryptoInitialPriceUSD.text.toString().toDouble()
            crypto.amount_invested_usd = binding.cryptoAmountInvestedUSD.text.toString().toDouble()
            crypto.current_price_usd = binding.cryptoCurrentPriceUSD.text.toString().toDouble()
            if (crypto.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_crypto_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            else {
                if (edit) {
                    app.cryptos.update(crypto.copy())
                } else {
                    app.cryptos.create(crypto.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
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