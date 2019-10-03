package com.elad.meetup.CryptoTesting

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.elad.meetup.model.CryptoCurrency
import com.elad.meetup.repo.CryptoCurrencyRepository
import com.elad.meetup.repo.network.ApiInterface
import com.elad.meetup.room.dbmodels.CryptoCurrencyDao
import com.elad.meetup.utils.Constants
import com.elad.meetup.utils.SharedPreferencesHelper
import com.elad.meetup.utils.Utils
import com.elad.meetup.viewmodel.CryptoCurrencyViewModel
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.internal.configuration.GlobalConfiguration.validate
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.TimeUnit


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
            arrayListOf(CryptoCurrency("elad"), CryptoCurrency("test"), CryptoCurrency("test2"))
        )

        cryptoCurrencyRepository.getCryptocurrencies()

        cryptoCurrencyViewModel.loadCryptocurrencies(50, 20)

        Thread.sleep(2000)
        assertTrue(cryptoCurrencyViewModel.isLiveDataResponseValid(cryptoCurrencyViewModel.cryptocurrenciesResult.value))
    }

    @After
    fun finish() {
        println("Testing view model successfully")
    }


}