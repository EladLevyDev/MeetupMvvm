package com.elad.meetup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elad.meetup.adapter.CryptoCurrencyAdapter
import com.elad.meetup.viewmodel.CryptoCurrencyViewModel
import com.elad.meetup.viewmodel.CryptoCurrencyViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import com.elad.meetup.model.CryptoCurrency
import com.elad.meetup.utils.Constants
import com.elad.meetup.utils.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var cryptoCurrencyViewModelFactory: CryptoCurrencyViewModelFactory

    private var cryptocurrenciesAdapter = CryptoCurrencyAdapter(ArrayList())
    private lateinit var cryptoCurrencyViewModel: CryptoCurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        initializeRecycler()
        initViewModelMethods()
        loadData()
    }

    private fun initViewModelMethods() {
        cryptoCurrencyViewModel = ViewModelProviders.of(this, cryptoCurrencyViewModelFactory).get(
            CryptoCurrencyViewModel::class.java
        )

        cryptoCurrencyViewModel.cryptocurrenciesResult().observe(this,
            Observer<List<CryptoCurrency>> {
                if (it != null) {
                    val position = cryptocurrenciesAdapter.itemCount
                    cryptocurrenciesAdapter.addCryptocurrencies(it)
                    recycler.adapter = cryptocurrenciesAdapter
                    recycler.scrollToPosition(position - Constants.LIST_SCROLLING)
                }
            })

        cryptoCurrencyViewModel.cryptocurrenciesError().observe(this, Observer<String> {
            if (it != null) {
                toast(it)
            }
        })

        cryptoCurrencyViewModel.cryptocurrenciesLoader().observe(this, Observer<Boolean> {
            if (it == false) progressBar.visibility = View.GONE
        })
    }

    private fun initializeRecycler() {
        val gridLayoutManager = GridLayoutManager(this, 1)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
        }
    }

    private  fun loadData() {
        progressBar.visibility = View.VISIBLE
        cryptoCurrencyViewModel.loadCryptocurrencies(Constants.LIMIT, Constants.OFFSET)
    }
}