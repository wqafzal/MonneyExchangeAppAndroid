package com.example.moneyexchangeapp.util

import com.example.moneyexchangeapp.data.model.ExchangeRate
import java.math.RoundingMode
import java.text.DecimalFormat

object ConversionUtils {
    fun updateConversionFromLatestRates(
        rateList: List<ExchangeRate>,
        base: String,
        amount: Double
    ): List<ExchangeRate> {
        val amountInBase =
            amount.div(rateList.first { it.symbol == base }.rate)
        return rateList.map {
            it.convertedAmount = amountInBase.times(it.rate)
            it
        }.toList()
    }

    fun formatAmount(amount: Double): String {
        val decimalFormat = DecimalFormat("#.####")
        decimalFormat.roundingMode = RoundingMode.FLOOR
        return decimalFormat.format(amount)
    }
}