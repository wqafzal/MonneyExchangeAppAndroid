package com.example.moneyexchangeapp.feature.exchange.data.api

/***
 * Single responsibility principle states that there should be only one reason to make a change in a clalss
 * Below classes are responsible only for mapping Json data from REST API
 */
data class LatestExchangeRateResponse(
    val base: String,
    val rates: List<ExchangeRateDto>,
    val timestamp: Int,
)

/***
 * A modal which is actually concerned in a response is known as a DTO
 * Normally we have domain modal for these classes
 */
data class ExchangeRateDto(
    val symbol: String,
    val rate: Double
)