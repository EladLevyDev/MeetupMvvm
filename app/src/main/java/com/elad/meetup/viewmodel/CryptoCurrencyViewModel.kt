package com.elad.meetup.viewmodel


import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elad.meetup.repo.CryptoCurrencyRepository
import com.elad.meetup.model.CreditCard
import kotlinx.coroutines.*

import javax.inject.Inject


class CryptoCurrencyViewModel @Inject constructor(private val cryptoCurrencyRepository: CryptoCurrencyRepository) :
    ViewModel() {

    var cryptocurrenciesResult: MutableLiveData<List<CreditCard>> = MutableLiveData()
    var cryptocurrenciesError: MutableLiveData<String> = MutableLiveData()
    var cryptocurrenciesLoader: MutableLiveData<Boolean> = MutableLiveData()

    fun cryptocurrenciesResult(): LiveData<List<CreditCard>> {
        return cryptocurrenciesResult
    }

    fun cryptocurrenciesError(): LiveData<String> {
        return cryptocurrenciesError
    }

    fun cryptocurrenciesLoader(): LiveData<Boolean> {
        return cryptocurrenciesLoader
    }


    fun loadCryptocurrencies(limit: Int, offset: Int) {
        // Dispatchers.IO (main-safety block)
        Build.VERSION_CODES.P
        CoroutineScope(Dispatchers.IO).launch {

            try {
                val cryptoCurrencies = cryptoCurrencyRepository.getCryptocurrencies()
                if (isLiveDataResponseValid(cryptoCurrencies)) {
                    cryptocurrenciesResult.postValue(cryptoCurrencies)
                }
                cryptocurrenciesLoader.postValue(false)

            } catch (e: Exception) {
                cryptocurrenciesLoader.postValue(false)
                e.printStackTrace()
            }

        }
    }

    fun isLiveDataResponseValid(it: List<CreditCard>?): Boolean {
        return it != null && it.isNotEmpty()
    }

}