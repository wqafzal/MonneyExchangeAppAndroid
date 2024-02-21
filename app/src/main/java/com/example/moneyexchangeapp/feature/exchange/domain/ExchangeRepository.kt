package com.example.moneyexchangeapp.feature.exchange.domain

import com.example.moneyexchangeapp.feature.exchange.domain.model.CurrencySymbol

interface ExchangeRepository {
    suspend fun getSymbols(): Result<List<CurrencySymbol>>

    fun getCurrencyRates()

    fun getHistoricalData()
}