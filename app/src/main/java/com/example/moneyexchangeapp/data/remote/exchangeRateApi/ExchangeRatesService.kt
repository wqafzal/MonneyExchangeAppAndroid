package com.example.moneyexchangeapp.data.remote.exchangeRateApi

import com.example.moneyexchangeapp.BuildConfig
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesService {

    @GET("latest.json")
    suspend fun getLatestRates(
        @Query(value = "app_id") appId: String = BuildConfig.apiKey,
        @Query(value = "base") convertFrom: String = "USD",
    ): LatestExchangeRateResponseModel
}