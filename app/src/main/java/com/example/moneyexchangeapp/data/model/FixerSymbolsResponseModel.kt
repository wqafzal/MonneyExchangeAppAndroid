package com.example.moneyexchangeapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class FixerSymbolsResponseModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,
    var success: Boolean,
    val symbols: List<Country>
)

@Entity
data class LatestExchangeRateResponseModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    val base: String,
    val date: String,
    val rates: List<ExchangeRate>,
    val success: Boolean,
    val timestamp: Int,
){
    @Ignore
    var error: Error?=null
}

data class ExchangeRate(
    val symbol: String,
    val rate: Double
)

data class Country(val name: String, val currencySymbol: String)