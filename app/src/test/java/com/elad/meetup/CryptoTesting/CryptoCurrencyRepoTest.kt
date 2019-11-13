package com.elad.meetup.CryptoTesting

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.elad.meetup.model.CryptoCurrency
import com.elad.meetup.repo.CryptoCurrencyRepository
import com.elad.meetup.repo.network.ApiInterface
import com.elad.meetup.room.dbmodels.CryptoCurrencyDao
import com.elad.meetup.utils.SharedPreferencesHelper
import com.elad.meetup.utils.Utils
import com.nhaarman.mockitokotlin2.mock
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
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

    @MockK
    private lateinit var cryptoCurrencyDB: CryptoCurrencyDao

    @MockK
    private lateinit var cryptoCurrency: CryptoCurrency

    private lateinit var utils: Utils
    private lateinit var apiInterface: ApiInterface
    private lateinit var sharedPreferences: SharedPreferencesHelper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        // init modules mock
        utils = Utils(context)
        apiInterface = mock()

        sharedPreferences = SharedPreferencesHelper(context.getSharedPreferences("cryptoTEST", Context.MODE_PRIVATE))

        // Create repo instance mock
        cryptoCurrencyRepository = CryptoCurrencyRepository(sharedPreferences, apiInterface, cryptoCurrencyDB, utils)
    }

    /*
        DB Tests
     */

    @Test
    fun isPrefsSavedCryptoCurrencySuccessfully() {
        every { cryptoCurrency.id } returns "2"
        every { cryptoCurrency.name } returns "bitcoin"
        every { cryptoCurrency.priceBtc } returns "4"

        cryptoCurrencyRepository.saveCryptoCurrency(cryptoCurrency)
        assert(cryptoCurrencyRepository.isSavedCryptoCurrencyValid())
    }


    @Test
    fun isPrefsSavedCryptoCurrencyFailed() {
        every { cryptoCurrency.id } returns ""
        every { cryptoCurrency.name } returns "bitcoin"
        every { cryptoCurrency.priceBtc } returns "4"

        cryptoCurrencyRepository.saveCryptoCurrency(cryptoCurrency)
        assert(!cryptoCurrencyRepository.isSavedCryptoCurrencyValid())
    }

    /*
        Api Tests
     */

    @Test
    fun isCryptoResponseReturnValidArray() = runBlocking {
        `when`(apiInterface.getCryptocurrencies()).thenReturn(
            arrayListOf(CryptoCurrency("elad"), CryptoCurrency("test"), CryptoCurrency("fail"))
        )
        val arrayResponse = cryptoCurrencyRepository.getCryptocurrenciesFromApi()
        assert(cryptoCurrencyRepository.isResponseValid(arrayResponse))
    }

    @Test
    fun isCryptoResponseReturnErrorWhenNoData() = runBlocking {
        `when`(apiInterface.getCryptocurrencies()).thenReturn(
            emptyList()
        )

        val arrayResponse = cryptoCurrencyRepository.getCryptocurrenciesFromApi()
        assert(!cryptoCurrencyRepository.isResponseValid(arrayResponse))

    }

    @After
    fun finish() {

    }
}