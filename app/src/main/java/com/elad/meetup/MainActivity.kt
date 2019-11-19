package com.elad.meetup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elad.meetup.adapter.CreditCardAdapter
import com.elad.meetup.viewmodel.CreditCardViewModel
import com.elad.meetup.viewmodel.CreditCardViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import com.elad.meetup.model.CreditCard
import com.elad.meetup.utils.Constants
import com.elad.meetup.utils.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var creditCardViewModelFactory: CreditCardViewModelFactory

    private var cryptocurrenciesAdapter = CreditCardAdapter(ArrayList())
    private lateinit var creditCardViewModel: CreditCardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        initializeRecycler()
        initViewModelMethods()
        loadData()
    }

    private fun initViewModelMethods() {
        creditCardViewModel = ViewModelProviders.of(this, creditCardViewModelFactory).get(
            CreditCardViewModel::class.java
        )

        creditCardViewModel.cryptocurrenciesResult().observe(this,
            Observer<List<CreditCard>> {
                if (it != null) {
                    val position = cryptocurrenciesAdapter.itemCount
                    cryptocurrenciesAdapter.addCryptocurrencies(it)
                    recycler.adapter = cryptocurrenciesAdapter
                    recycler.scrollToPosition(position - Constants.LIST_SCROLLING)
                }
            })

        creditCardViewModel.cryptocurrenciesError().observe(this, Observer<String> {
            if (it != null) {
                toast(it)
            }
        })

        creditCardViewModel.cryptocurrenciesLoader().observe(this, Observer<Boolean> {
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
        creditCardViewModel.loadCryptocurrencies(Constants.LIMIT, Constants.OFFSET)
    }
}