package com.elad.meetup.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elad.meetup.R

import com.elad.meetup.model.CreditCard
import java.util.*

class CreditCardAdapter(
  creditCards: List<CreditCard>?) : RecyclerView.Adapter<CreditCardAdapter.CryptocurrencieViewHolder>() {

  private var cryptocurrenciesList = ArrayList<CreditCard>()

  init {
    this.cryptocurrenciesList = creditCards as ArrayList<CreditCard>
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

  fun addCryptocurrencies(creditCards: List<CreditCard>) {
    val initPosition = cryptocurrenciesList.size
    cryptocurrenciesList.addAll(creditCards)
    notifyItemRangeInserted(initPosition, cryptocurrenciesList.size)
  }

  class CryptocurrencieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var cryptocurrencyId = itemView.findViewById<TextView>(R.id.cryptocurrency_id)!!
    private var cryptocurrencyRate = itemView.findViewById<TextView>(R.id.cryptocurrency_rate)!!

    fun cryptocurrencyListItem(creditCardItem: CreditCard) {
      cryptocurrencyId.text = creditCardItem.id
    }
  }
}