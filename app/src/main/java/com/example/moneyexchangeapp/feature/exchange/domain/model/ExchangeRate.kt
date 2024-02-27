package com.example.moneyexchangeapp.feature.exchange.domain.model

data class ExchangeRate(
    val symbol: String,
    val rate: Double
) {
    var convertedAmount: Double = 0.0
}