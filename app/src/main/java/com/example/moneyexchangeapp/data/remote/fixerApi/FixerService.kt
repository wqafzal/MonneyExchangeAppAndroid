package com.example.moneyexchangeapp.data.remote.fixerApi

import com.example.moneyexchangeapp.data.HistoricalDataResponseModel
import com.example.moneyexchangeapp.data.model.ConvertDataResponse
import com.example.moneyexchangeapp.data.model.FixerSymbolsResponseModel
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface FixerService {
    @GET("symbols")
    suspend fun getSymbols(
    ): FixerSymbolsResponseModel

    @GET("convert")
    suspend fun getConvertedResult(
        @Query(value = "from") convertFrom: String,
        @Query(value = "to") convertTo: String,
        @Query(value = "amount") amount: String
    ): ConvertDataResponse

    @GET("latest")
    suspend fun getLatestRates(
        @Query(value = "base") convertFrom: String = "EUR",
    ): LatestExchangeRateResponseModel

    @GET("timeseries")
    suspend fun getHistoricalData(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String,
    ): HistoricalDataResponseModel
}