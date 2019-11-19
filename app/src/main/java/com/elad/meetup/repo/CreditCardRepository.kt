package com.elad.meetup.repo

import android.text.TextUtils
import com.elad.meetup.repo.network.ApiInterface
import com.elad.meetup.model.CreditCard
import com.elad.meetup.room.dbmodels.CreditCardDao
import com.elad.meetup.utils.SharedPreferencesHelper
import com.elad.meetup.utils.Utils
import javax.inject.Inject


class CreditCardRepository @Inject constructor(
    private val sharedPreferences: SharedPreferencesHelper,
    private val apiInterface: ApiInterface,
    private val creditCardDao: CreditCardDao,
    private val utils: Utils
) {


    companion object {
        const val PREF_KEY_CRYPTO_NAME = "PREF_KEY_CRYPTO_NAME"
    }

    suspend fun getCryptocurrencies(): List<CreditCard> {
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

    suspend fun updateDB(data: List<CreditCard>) {
        for (item in data) {
            creditCardDao.insertCryptocurrency(item)
        }
    }

    fun saveCryptoCurrency(crypto: CreditCard) {
        sharedPreferences.saveCryptoslInfo(crypto)
    }

    fun isSavedCryptoCurrencyValid(): Boolean {
        return sharedPreferences.isCryptoCurrencyValid
    }


    suspend fun getCryptocurrenciesFromApi(): List<CreditCard> {
        return apiInterface.getCryptocurrencies()

    }

    suspend fun getCryptocurrenciesFromDb(limit: Int, offset: Int): List<CreditCard> {
        return creditCardDao.queryCryptocurrencies(limit, offset)
    }

    fun isResponseValid(arrayResponse: List<CreditCard>): Boolean {
        return arrayResponse.size > 0 && !TextUtils.isEmpty(arrayResponse[0].preffix)
    }


}