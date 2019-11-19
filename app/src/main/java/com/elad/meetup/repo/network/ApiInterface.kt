package com.elad.meetup.repo.network

import retrofit2.http.GET
import retrofit2.http.Query
import com.elad.meetup.model.CreditCard


interface ApiInterface {

    @GET("ticker/")
    suspend
    fun getCryptocurrencies(@Query("start") start: String = "0"): List<CreditCard>
}