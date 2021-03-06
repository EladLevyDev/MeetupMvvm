package com.elad.meetup.repo

import android.text.TextUtils
import com.elad.meetup.repo.network.ApiInterface
import com.elad.meetup.model.CryptoCurrency
import com.elad.meetup.room.dbmodels.CryptoCurrencyDao
import com.elad.meetup.utils.Constants
import com.elad.meetup.utils.SharedPreferencesHelper
import com.elad.meetup.utils.Utils
import javax.inject.Inject


class CryptoCurrencyRepository @Inject constructor(
    private val sharedPreferences: SharedPreferencesHelper,
    private val apiInterface: ApiInterface,
    private val cryptoCurrencyDao: CryptoCurrencyDao,
    private val utils: Utils
) {


    companion object {
        const val PREF_KEY_CRYPTO_NAME = "PREF_KEY_CRYPTO_NAME"
    }

    suspend fun getCryptocurrencies(): List<CryptoCurrency> {
        val hasConnection = utils.isConnectedToInternet()
        if (hasConnection) {
            val crypto = getCryptocurrenciesFromApi();
            saveCryptoCurrency(crypto[0])
            updateDB(crypto)
            return crypto
        } else {
            return getCryptocurrenciesFromDb(10, 50)
        }
    }

    suspend fun updateDB(data: List<CryptoCurrency>) {
        for (item in data) {
            cryptoCurrencyDao.insertCryptocurrency(item)
        }
    }

    fun saveCryptoCurrency(crypto: CryptoCurrency) {
        sharedPreferences.saveCryptoslInfo(crypto)
    }

    fun isSavedCryptoCurrencyValid(): Boolean {
        return sharedPreferences.isCryptoCurrencyValid
    }


    suspend fun getCryptocurrenciesFromApi(): List<CryptoCurrency> {
        return apiInterface.getCryptocurrencies()

    }

    suspend fun getCryptocurrenciesFromDb(limit: Int, offset: Int): List<CryptoCurrency> {
        return cryptoCurrencyDao.queryCryptocurrencies(limit, offset)
    }

    fun isResponseValid(arrayResponse: List<CryptoCurrency>): Boolean {
        return arrayResponse.size > 0 && !TextUtils.isEmpty(arrayResponse[0].name)
    }


}