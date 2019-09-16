package com.elad.meetup

import android.os.Build
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
//@Config(sdk = [Build.VERSION_CODES.N])
class UtilsUnitTest {



    // context
   // val context = ApplicationProvider.getApplicationContext<Context>()

    // utils
  //  var utils: Utils

    init {

       // utils = Utils(context)
    }


    @Test
    fun isNetworkConnectedIsCorrect() {
        assertEquals(2+2, 4)
    }
}
