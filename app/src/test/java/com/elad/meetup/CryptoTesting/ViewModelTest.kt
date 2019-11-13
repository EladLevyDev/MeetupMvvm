package com.elad.meetup.CryptoTesting

import android.content.Context

import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.elad.meetup.model.CryptoCurrency
import com.elad.meetup.repo.CryptoCurrencyRepository
import com.elad.meetup.repo.network.ApiInterface
import com.elad.meetup.room.dbmodels.CryptoCurrencyDao
import com.elad.meetup.utils.SharedPreferencesHelper
import com.elad.meetup.utils.Utils
import com.elad.meetup.viewmodel.CryptoCurrencyViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@Config()
@RunWith(RobolectricTestRunner::class)
class ViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    // Context
    val context = ApplicationProvider.getApplicationContext<Context>()

    // Repo modules
    private lateinit var cryptoCurrencyDB: CryptoCurrencyDao
    private lateinit var utils: Utils
    private lateinit var apiInterface: ApiInterface
    private lateinit var sharedPreferences: SharedPreferencesHelper

    // Repo
    private lateinit var cryptoCurrencyRepository: CryptoCurrencyRepository

    // ViewModel modules
    private lateinit var cryptoCurrencyViewModel: CryptoCurrencyViewModel

    // Mock observer for live data
    private val observer: Observer<List<CryptoCurrency>> = mock()

    @Before
    fun setUp() {
        println("Testing view model")

        // init modules mock
        utils = Utils(context)

        apiInterface = mock()
        cryptoCurrencyDB = mock()

        sharedPreferences = SharedPreferencesHelper(context.getSharedPreferences("cryptoTEST", Context.MODE_PRIVATE))


        // Create repo instance mock
        cryptoCurrencyRepository = CryptoCurrencyRepository(sharedPreferences, apiInterface, cryptoCurrencyDB, utils)

        // Create viewmodel instance
        cryptoCurrencyViewModel = CryptoCurrencyViewModel(cryptoCurrencyRepository)

        cryptoCurrencyViewModel.cryptocurrenciesResult.observeForever(observer)

    }

    @Test
    fun isLiveDataGetUpdatedAfterResponse() = runBlocking {

        /*
            Mock response from api
         */
        `when`(cryptoCurrencyRepository.getCryptocurrencies()).thenReturn(
            arrayListOf(CryptoCurrency("bitcoin"), CryptoCurrency("ether"), CryptoCurrency("test2"))
        )

        cryptoCurrencyRepository.getCryptocurrencies()

        cryptoCurrencyViewModel.loadCryptocurrencies(50, 20)

        Thread.sleep(2000)
        assertTrue(cryptoCurrencyViewModel.isLiveDataResponseValid(cryptoCurrencyViewModel.cryptocurrenciesResult.value))
    }

    @After
    fun finish() {
        println("Test finished successfully")
    }


}