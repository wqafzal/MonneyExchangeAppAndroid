package com.example.moneyexchangeapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LatestExchangeRateResponseModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    val base: String,
    val rates: List<ExchangeRate>,
    val timestamp: Int,
)

data class ExchangeRate(
    val symbol: String,
    val rate: Double
){
    var convertedAmount: Double = 0.0
}