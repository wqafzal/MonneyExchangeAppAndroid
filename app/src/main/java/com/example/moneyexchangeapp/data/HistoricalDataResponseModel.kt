package com.example.moneyexchangeapp.data

import com.example.moneyexchangeapp.data.model.ExchangeRate

data class HistoricalDataResponseModel(
    val base: String,
    val endDate: String,
    val rates: List<HistoricalData>,
    val startDate: String,
    val success: Boolean,
    val timeseries: Boolean
)

data class HistoricalData(
    var date: String,
    var currencies: List<ExchangeRate>
)