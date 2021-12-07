package org.wit.cryptocurrency.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.cryptocurrency.R
import org.wit.cryptocurrency.databinding.ActivityCryptocurrencyBinding
import org.wit.cryptocurrency.helpers.showImagePicker
import org.wit.cryptocurrency.main.MainApp
import org.wit.cryptocurrency.models.CryptocurrencyModel
import org.wit.cryptocurrency.models.Location
import timber.log.Timber
import timber.log.Timber.i

class CryptocurrencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCryptocurrencyBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    var crypto = CryptocurrencyModel()
    lateinit var app : MainApp


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

            Picasso.get()
                .load(crypto.image)
                .into(binding.cryptoImage)
            if (crypto.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_crypto_image)
            }
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

        binding.chooseImage.setOnClickListener {
            i("Select Image")
            showImagePicker(imageIntentLauncher)
        }

        binding.cryptoLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (crypto.zoom != 0f) {
                location.lat =  crypto.lat
                location.lng = crypto.lng
                location.zoom = crypto.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            crypto.image = result.data!!.data!!
                            Picasso.get()
                                .load(crypto.image)
                                .into(binding.cryptoImage)
                            binding.chooseImage.setText(R.string.change_crypto_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            crypto.lat = location.lat
                            crypto.lng = location.lng
                            crypto.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}