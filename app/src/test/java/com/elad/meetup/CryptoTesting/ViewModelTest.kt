package com.elad.meetup.CryptoTesting

import android.content.Context

import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.elad.meetup.model.CreditCard
import com.elad.meetup.repo.CreditCardRepository
import com.elad.meetup.repo.network.ApiInterface
import com.elad.meetup.room.dbmodels.CreditCardDao
import com.elad.meetup.utils.SharedPreferencesHelper
import com.elad.meetup.utils.Utils
import com.elad.meetup.viewmodel.CreditCardViewModel
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
    private lateinit var creditCardDB: CreditCardDao
    private lateinit var utils: Utils
    private lateinit var apiInterface: ApiInterface
    private lateinit var sharedPreferences: SharedPreferencesHelper

    // Repo
    private lateinit var creditCardRepository: CreditCardRepository

    // ViewModel modules
    private lateinit var creditCardViewModel: CreditCardViewModel

    // Mock observer for live data
    private val observer: Observer<List<CreditCard>> = mock()

    @Before
    fun setUp() {
        println("Testing view model")

        // init modules mock
        utils = Utils(context)

        apiInterface = mock()
        creditCardDB = mock()

        sharedPreferences = SharedPreferencesHelper(context.getSharedPreferences("cryptoTEST", Context.MODE_PRIVATE))


        // Create repo instance mock
        creditCardRepository = CreditCardRepository(sharedPreferences, apiInterface, creditCardDB, utils)

        // Create viewmodel instance
        creditCardViewModel = CreditCardViewModel(creditCardRepository)

        creditCardViewModel.cryptocurrenciesResult.observeForever(observer)

    }

    @Test
    fun isLiveDataGetUpdatedAfterResponse() = runBlocking {

        /*
            Mock response from api
         */
        `when`(creditCardRepository.getCryptocurrencies()).thenReturn(
            arrayListOf(CreditCard("bitcoin"), CreditCard("ether"), CreditCard("test2"))
        )

        creditCardRepository.getCryptocurrencies()

        creditCardViewModel.loadCryptocurrencies(50, 20)

        Thread.sleep(2000)
        assertTrue(creditCardViewModel.isLiveDataResponseValid(creditCardViewModel.cryptocurrenciesResult.value))
    }


    @After
    fun finish() {
        println("Test finished successfully")
    }


}