package org.wit.cryptocurrency.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.cryptocurrency.databinding.CardCryptocurrencyBinding
import org.wit.cryptocurrency.models.CryptocurrencyModel

interface CryptocurrencyListener {
    fun onCryptoClick(crypto: CryptocurrencyModel)
}

class CryptocurrencyAdapter constructor(private var cryptos: List<CryptocurrencyModel>,
                                        private val listener: CryptocurrencyListener) :
    RecyclerView.Adapter<CryptocurrencyAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardCryptocurrencyBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val crypto = cryptos[holder.adapterPosition]
        holder.bind(crypto, listener)
    }

    override fun getItemCount(): Int = cryptos.size

    class MainHolder(private val binding : CardCryptocurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(crypto: CryptocurrencyModel, listener: CryptocurrencyListener) {
            binding.cryptoName.text = crypto.name
            binding.cryptoSymbol.text = crypto.symbol
            Picasso.get().load(crypto.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onCryptoClick(crypto)}
        }
    }
}