package com.example.moneyexchangeapp.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moneyexchangeapp.data.local.room.converters.CurrencyRatesConverter
import com.example.moneyexchangeapp.data.local.room.dao.CurrencyRateDao
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.core.util.Constants

@Database(
    entities = [LatestExchangeRateResponseModel::class],
    version = Constants.DataBase.VERSION_N0,
    exportSchema = false
)
@TypeConverters(CurrencyRatesConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getCurrencyRateDao(): CurrencyRateDao
}