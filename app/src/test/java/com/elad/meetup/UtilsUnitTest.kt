package com.elad.meetup

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.elad.meetup.model.CryptoCurrency
import com.elad.meetup.utils.Utils
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
//@Config(sdk = [Build.VERSION_CODES.N])
class UtilsUnitTest {


    // context
    val context = ApplicationProvider.getApplicationContext<Context>()

    // utils
    var utils: Utils

    init {
        utils = Utils(context)
    }


    @Test
    fun isNetworkConnectedReturnNotConnected() {
        assertFalse(utils.isConnectedToInternet())
    }

    @Test
    fun isNumberIsBigger() {
        assertTrue(utils.isNumberBigger(4, 2))
    }

    @Test
    fun isNumberIsSmaller() {
        assertFalse(utils.isNumberBigger(2, 4))
    }

    @Test
    fun isCyptoCurrencyValid() {
        val CryptoCurrency = CryptoCurrency()
        CryptoCurrency.name = "bitcoin"
        assertTrue(utils.isCryptoCurrencyIsValid(CryptoCurrency))
    }
}
