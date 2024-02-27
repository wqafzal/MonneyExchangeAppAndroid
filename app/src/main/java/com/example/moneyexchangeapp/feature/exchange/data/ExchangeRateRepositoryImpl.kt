package com.example.moneyexchangeapp.feature.exchange.data

import com.example.moneyexchangeapp.core.util.ConversionUtils
import com.example.moneyexchangeapp.feature.exchange.data.api.ExchangeRatesService
import com.example.moneyexchangeapp.feature.exchange.data.db.ExchangeRateDao
import com.example.moneyexchangeapp.feature.exchange.data.db.ExchangeRateEntity
import com.example.moneyexchangeapp.feature.exchange.domain.ExchangeRepository
import com.example.moneyexchangeapp.feature.exchange.domain.model.CurrencySymbol
import com.example.moneyexchangeapp.feature.exchange.domain.model.ExchangeRate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val exchangeRatesService: ExchangeRatesService,
    private val exchangeRateDao: ExchangeRateDao
) : ExchangeRepository {

    private var latestRates: List<ExchangeRate> = emptyList()
    override fun getSymbols(): Flow<List<ExchangeRate>> {
        return exchangeRateDao.getLatestRates(base = "USD").map {
            latestRates = it.map {row ->
                ExchangeRate(
                    symbol = row.symbol,
                    rate = row.rate
                )
            }
            latestRates
        }
    }

    override suspend fun fetLatestRates(base: String) {
        val response = exchangeRatesService.getLatestRates(convertFrom = base)
        val rows = response.rates.map {
            ExchangeRateEntity(
                base = base,
                symbol = it.symbol,
                rate = it.rate,
                timeStamp = response.timestamp
            )
        }.toList()
        exchangeRateDao.insertAll(rows = rows)
    }

    override suspend fun getLatestExchangeRates(base: String, amount: Double): List<ExchangeRate> {
        val amountInBase =
            amount.div(latestRates.first { it.symbol == base }.rate)
        return latestRates.map {
            it.convertedAmount = amountInBase.times(it.rate)
            it
        }.toList()
    }
}