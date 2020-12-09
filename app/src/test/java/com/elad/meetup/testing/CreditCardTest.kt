package com.elad.meetup.testing

import com.elad.meetup.model.CreditCard
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.sql.DriverManager.println

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(BlockJUnit4ClassRunner::class)
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
        Assert.assertTrue(CreditCard.UP_CARD, creditCard.isTypeUpCard())
    }

    @Test
    fun testIsCreditCardTypeNotUpCard() {
        // Mock object data
        creditCard.id = (CreditCard.MASTER_CARD)

        // Uses real production code
        Assert.assertFalse(creditCard.isTypeUpCard())
    }

    /*
        This test will failed for intro purpose;
     */

    @Test
    fun testWillFailedForIntro() {
        // Mock object data
        creditCard.id = (CreditCard.VISA)

        // Uses real production code
        Assert.assertTrue(creditCard.isTypeUpCard())
    }

    @After
    fun finishTesting() {
        println("Finished Test Successfully")
    }

}
