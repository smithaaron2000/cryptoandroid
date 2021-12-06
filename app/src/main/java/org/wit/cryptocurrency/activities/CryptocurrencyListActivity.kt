package org.wit.cryptocurrency.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.cryptocurrency.R
import org.wit.cryptocurrency.adapters.CryptocurrencyAdapter
import org.wit.cryptocurrency.adapters.CryptocurrencyListener
import org.wit.cryptocurrency.databinding.ActivityCryptocurrencyListBinding
import org.wit.cryptocurrency.main.MainApp
import org.wit.cryptocurrency.models.CryptocurrencyModel

class CryptocurrencyListActivity : AppCompatActivity(), CryptocurrencyListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityCryptocurrencyListBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCryptocurrencyListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = CryptocurrencyAdapter(app.cryptos.findAll(), this)

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, CryptocurrencyActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCryptoClick(crypto: CryptocurrencyModel) {
        val launcherIntent = Intent(this, CryptocurrencyActivity::class.java)
        launcherIntent.putExtra("cryptocurrency_edit", crypto)
        refreshIntentLauncher.launch(launcherIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }
}