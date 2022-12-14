package com.example.moneyexchangeapp.data.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moneyexchangeapp.data.local.room.converters.CountryConverter
import com.example.moneyexchangeapp.data.local.room.converters.CurrencyRatesConverter
import com.example.moneyexchangeapp.data.local.room.dao.CurrencyRateDao
import com.example.moneyexchangeapp.data.local.room.dao.SymbolsDao
import com.example.moneyexchangeapp.data.model.FixerSymbolsResponseModel
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.util.Constants

@Database(
    entities = [LatestExchangeRateResponseModel::class, FixerSymbolsResponseModel::class],
    version = Constants.DataBase.VERSION_N0,
    exportSchema = false
)
@TypeConverters(CurrencyRatesConverter::class, CountryConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getCurrencyRateDao(): CurrencyRateDao
    abstract fun getSymbolsDao(): SymbolsDao
}