package com.elad.meetup.repo

import com.elad.meetup.repo.network.ApiInterface
import com.elad.meetup.model.CryptoCurrency
import us.egek92.mvvm.persistance.dao.CryptoCurrencyDao
import com.elad.meetup.utils.Constants
import com.elad.meetup.utils.Utils
import javax.inject.Inject


class CryptoCurrencyRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val cryptoCurrencyDao: CryptoCurrencyDao,
    private val utils: Utils
) {

    suspend fun getCryptocurrencies(limit: Int, offset: Int): List<CryptoCurrency> {
        val hasConnection = utils.isConnectedToInternet()
        if (hasConnection) {
            val crypto = getCryptocurrenciesFromApi();
            updateDB(crypto)
            return crypto
        } else {
            return getCryptocurrenciesFromDb(limit, offset)
        }
    }

    private suspend fun updateDB(data: List<CryptoCurrency>) {
        for (item in data) {
            cryptoCurrencyDao.insertCryptocurrency(item)
        }
    }

    private suspend fun getCryptocurrenciesFromApi(): List<CryptoCurrency> {
        return apiInterface.getCryptocurrencies(Constants.START_ZERO_VALUE)

    }

    private suspend fun getCryptocurrenciesFromDb(limit: Int, offset: Int): List<CryptoCurrency> {
        return cryptoCurrencyDao.queryCryptocurrencies(limit, offset)
    }
}