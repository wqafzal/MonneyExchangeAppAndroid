package com.example.moneyexchangeapp.data.local.room.converters

import androidx.room.TypeConverter
import com.example.moneyexchangeapp.data.model.Country
import com.example.moneyexchangeapp.data.model.ExchangeRate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.HashMap


class CountryConverter {
    @TypeConverter
    fun fromCurrencySymbol(rates: String): List<Country> {
        return Gson().fromJson(rates, object : TypeToken<List<Country>>() {}.type)
    }

    @TypeConverter
    fun toCurrencySymbol(rates: List<Country>): String {
        return Gson().toJson(rates)
    }
}