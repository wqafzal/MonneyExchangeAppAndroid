package com.example.moneyexchangeapp.feature.exchange.domain

import com.example.moneyexchangeapp.feature.exchange.domain.model.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    fun getSymbols(): Flow<List<ExchangeRate>>

    suspend fun fetLatestRates(base: String)

    suspend fun getLatestExchangeRates(base: String, amount: Double): List<ExchangeRate>
}