package com.elad.meetup.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elad.meetup.R

import com.elad.meetup.model.CryptoCurrency
import java.util.*

class CryptoCurrencyAdapter(
    cryptoCurrencies: List<CryptoCurrency>?) : RecyclerView.Adapter<CryptoCurrencyAdapter.CryptocurrencieViewHolder>() {

  private var cryptocurrenciesList = ArrayList<CryptoCurrency>()

  init {
    this.cryptocurrenciesList = cryptoCurrencies as ArrayList<CryptoCurrency>
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrencieViewHolder {
    val itemView = LayoutInflater.from(parent?.context).inflate(
      R.layout.cryptocurrency_list_item,
        parent, false)
    return CryptocurrencieViewHolder(itemView)
  }

  override fun getItemCount(): Int {
    return cryptocurrenciesList.size
  }

  override fun onBindViewHolder(holder: CryptocurrencieViewHolder, position: Int) {
    val cryptocurrencyItem = cryptocurrenciesList[position]
    holder.cryptocurrencyListItem(cryptocurrencyItem)
  }

  fun addCryptocurrencies(cryptoCurrencies: List<CryptoCurrency>) {
    val initPosition = cryptocurrenciesList.size
    cryptocurrenciesList.addAll(cryptoCurrencies)
    notifyItemRangeInserted(initPosition, cryptocurrenciesList.size)
  }

  class CryptocurrencieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var cryptocurrencyId = itemView.findViewById<TextView>(R.id.cryptocurrency_id)!!
    private var cryptocurrencyRate = itemView.findViewById<TextView>(R.id.cryptocurrency_rate)!!

    fun cryptocurrencyListItem(cryptoCurrencyItem: CryptoCurrency) {
      cryptocurrencyId.text = cryptoCurrencyItem.id
      cryptocurrencyRate.text = cryptoCurrencyItem.priceBtc
    }
  }
}