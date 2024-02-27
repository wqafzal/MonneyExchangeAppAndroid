package com.example.moneyexchangeapp.feature.exchange.data.api

import com.example.moneyexchangeapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesService {

    @GET("latest.json")
    suspend fun getLatestRates(
        @Query(value = "app_id") appId: String = BuildConfig.apiKey, // should be moved inside interceptor
        @Query(value = "base") convertFrom: String = "USD",
    ): LatestExchangeRateResponse
}