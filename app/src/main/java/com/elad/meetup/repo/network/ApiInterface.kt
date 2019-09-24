package com.elad.meetup.repo.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import com.elad.meetup.model.CryptoCurrency
import kotlinx.coroutines.Deferred


interface ApiInterface {

    @GET("ticker/")
    suspend
    fun getCryptocurrencies(@Query("start") start: String = "0"): List<CryptoCurrency>
}