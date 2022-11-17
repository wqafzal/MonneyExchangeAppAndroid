package com.example.moneyexchangeapp.data.local.room.converters

import androidx.room.TypeConverter
import com.example.moneyexchangeapp.data.model.ExchangeRate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CurrencyRatesConverter {
    @TypeConverter
    fun fromRates(rates: String): List<ExchangeRate> {
        return Gson().fromJson(rates, object : TypeToken<List<ExchangeRate>>() {}.type)
    }

    @TypeConverter
    fun toRates(rates: List<ExchangeRate>): String {
        return Gson().toJson(rates)
    }
}