package com.elad.meetup

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.elad.meetup.model.CryptoCurrency
import com.elad.meetup.repo.CryptoCurrencyRepository
import com.elad.meetup.repo.network.ApiInterface
import com.elad.meetup.room.dbmodels.CryptoCurrencyDao
import com.elad.meetup.utils.SharedPreferencesHelper
import com.elad.meetup.utils.Utils
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class CryptoCurrencyRepoTest {

    val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var cryptoCurrencyRepository: CryptoCurrencyRepository


    private lateinit var cryptoCurrencyDB: CryptoCurrencyDao
    private lateinit var utils: Utils
    private lateinit var apiInterface: ApiInterface

    private lateinit var sharedPreferences: SharedPreferencesHelper

    @Before
    fun setUp() {
        println("Starting testing CryptoCurrencyRepo")
        utils = Utils(context)
        apiInterface = mock()
        cryptoCurrencyDB = mock()
        sharedPreferences = SharedPreferencesHelper(context.getSharedPreferences("cryptoTEST", Context.MODE_PRIVATE))
        cryptoCurrencyRepository = CryptoCurrencyRepository(sharedPreferences, apiInterface, cryptoCurrencyDB, utils)
    }

    @Test
    fun isPrefsSavedCryptoCurrency() {
        val cryptoCurrency = CryptoCurrency()
        cryptoCurrency.name = "elad"
        cryptoCurrency.id = "2"
        cryptoCurrency.priceBtc = "4"

        cryptoCurrencyRepository.saveCryptoCurrency(cryptoCurrency)
        assert(cryptoCurrencyRepository.isSavedCryptoCurrencyValid())

    }

    @After
    fun finish() {
        println("Finishing testing CryptoCurrencyRepo")
    }
}