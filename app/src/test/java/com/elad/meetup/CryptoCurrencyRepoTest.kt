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
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class CryptoCurrencyRepoTest {

    // Get application context
    val context = ApplicationProvider.getApplicationContext<Context>()

    // Repo
    private lateinit var cryptoCurrencyRepository: CryptoCurrencyRepository


    // Mock modules
    private lateinit var cryptoCurrencyDB: CryptoCurrencyDao
    private lateinit var utils: Utils
    private lateinit var apiInterface: ApiInterface
    private lateinit var sharedPreferences: SharedPreferencesHelper

    @Before
    fun setUp() {
        println("Starting testing CryptoCurrencyRepo")

        // init modules mock
        utils = Utils(context)
        apiInterface = mock()
        cryptoCurrencyDB = mock()
        sharedPreferences = SharedPreferencesHelper(context.getSharedPreferences("cryptoTEST", Context.MODE_PRIVATE))

        // Create repo instance mock
        cryptoCurrencyRepository = CryptoCurrencyRepository(sharedPreferences, apiInterface, cryptoCurrencyDB, utils)
    }

    /*
        DB Tests
     */
    @Test
    fun isPrefsSavedCryptoCurrencySuccessfully() {
        val cryptoCurrency = CryptoCurrency("elad")
        cryptoCurrency.id = "2"
        cryptoCurrency.priceBtc = "4"

        cryptoCurrencyRepository.saveCryptoCurrency(cryptoCurrency)
        assert(cryptoCurrencyRepository.isSavedCryptoCurrencyValid())
    }

    /*
        Api Tests
     */

    @Test
    fun isCryptoResponseReturnValidArray() = runBlocking {
        `when`(apiInterface.getCryptocurrencies("0")).thenReturn(
            arrayListOf(CryptoCurrency("elad"), CryptoCurrency("test"), CryptoCurrency("fail"))
        )
        val arrayResponse = cryptoCurrencyRepository.getCryptocurrenciesFromApi()
        assert(cryptoCurrencyRepository.isResponseValid(arrayResponse))
    }

    @Test
    fun isCryptoResponseReturnErrorWhenNoData() = runBlocking {
        `when`(apiInterface.getCryptocurrencies("0")).thenReturn(
            emptyList()
        )

        val arrayResponse = cryptoCurrencyRepository.getCryptocurrenciesFromApi()
        assert(!cryptoCurrencyRepository.isResponseValid(arrayResponse))
    }

    @After
    fun finish() {
        println("Finishing testing CryptoCurrencyRepo")
    }
}