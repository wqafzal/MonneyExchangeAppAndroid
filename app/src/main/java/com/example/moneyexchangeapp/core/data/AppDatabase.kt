package com.example.moneyexchangeapp.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneyexchangeapp.core.util.Constants
import com.example.moneyexchangeapp.feature.exchange.data.db.ExchangeRateDao
import com.example.moneyexchangeapp.feature.exchange.data.db.ExchangeRateEntity

@Database(
    entities = [ExchangeRateEntity::class],
    version = Constants.DataBase.VERSION_N0,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getExchangeRateDao(): ExchangeRateDao
}