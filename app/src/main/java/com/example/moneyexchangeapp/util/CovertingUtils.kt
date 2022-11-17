package com.example.moneyexchangeapp.util

import com.example.moneyexchangeapp.data.model.ExchangeRate

object ConversionUtils {
    fun updateConversionFromLatestRates(
        rateList: List<ExchangeRate>,
        convertFrom: String,
        convertTo: String,
        amount: Double
    ): Double {

        val amountInEuros =
            amount.div(rateList.first { it.symbol == convertFrom }.rate)
        return amountInEuros.times(rateList.first { it.symbol == convertTo }.rate)

    }
}