package com.example.moneyexchangeapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LatestExchangeRateResponseModel(
    val base: String,
    val rates: List<ExchangeRate>,
    val timestamp: Int,
){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}

data class ExchangeRate(
    val symbol: String,
    val rate: Double
){
    var convertedAmount: Double = 0.0
}