package com.elad.meetup.CryptoTesting

import com.elad.meetup.model.CreditCard
import org.junit.After
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
class CreditCardTest {

    // Model
    lateinit var creditCard: CreditCard

    @Before
    fun setUp() {
        creditCard = CreditCard(CreditCard.UP_CARD)
    }

    @Test
    fun testIsCreditCardTypeUpCard() {
        // Uses real production code
        assert(creditCard.isTypeUpCard())
    }

    @Test
    fun testIsCreditCardTypeNotUpCard() {
        // Mock object data
        creditCard.id = (CreditCard.MASTER_CARD)

        // Uses real production code
        assert(!creditCard.isTypeUpCard())
    }

    /*
        This test will failed for intro purpose
     */

    @Test
    fun testFailedForIntro() {
        // Mock object data
        creditCard.id = (CreditCard.VISA)

        // Uses real production code
        assert(creditCard.isTypeUpCard())
    }

    @After
    fun finishTesting() {
        println("Finished Successfully")
    }


}
