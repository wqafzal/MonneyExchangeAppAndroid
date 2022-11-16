package com.example.moneyexchangeapp.data.remote.fixerApi

import com.example.moneyexchangeapp.BuildConfig
import com.example.moneyexchangeapp.data.model.ConvertDataResponse
import com.example.moneyexchangeapp.data.model.FixerSymbolsResponseModel
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixerService {
    @GET("symbols")
    suspend fun getSymbols(
    ): FixerSymbolsResponseModel

    @GET("api/{date}")
    suspend fun getRecordsByDate(
        @Path(value = "date") date: String,
        @Query(value = "apikey") apiKey: String = BuildConfig.apiKey,
        @Query(value = "symbols") symbols: String  = "USD,AUD,CAD,PLN,MXN,NGN"
    ): Response<FixerSymbolsResponseModel>

    @GET("convert")
    suspend fun getConvertedResult(
        @Query(value = "from") convertFrom: String,
        @Query(value = "to") convertTo: String,
        @Query(value = "amount") amount: String
    ): ConvertDataResponse

    @GET("convert")
    suspend fun getLatestRates(
        @Query(value = "base") convertFrom: String = "EUR",
    ): LatestExchangeRateResponseModel
}