package org.wit.cryptocurrency.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.cryptocurrency.databinding.CardCryptocurrencyBinding
import org.wit.cryptocurrency.models.CryptocurrencyModel

class CryptocurrencyAdapter constructor(private var cryptos: List<CryptocurrencyModel>) :
    RecyclerView.Adapter<CryptocurrencyAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardCryptocurrencyBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val crypto = cryptos[holder.adapterPosition]
        holder.bind(crypto)
    }

    override fun getItemCount(): Int = cryptos.size

    class MainHolder(private val binding : CardCryptocurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(crypto: CryptocurrencyModel) {
            binding.cryptoName.text = crypto.name
            binding.cryptoSymbol.text = crypto.symbol
        }
    }
}