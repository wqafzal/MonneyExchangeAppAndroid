package com.example.moneyexchangeapp.data.local.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.*
import com.example.moneyexchangeapp.data.local.room.converters.CurrencyRatesConverter
import com.example.moneyexchangeapp.data.local.room.dao.CurrencyRateDao
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.di.DataModule
import com.example.moneyexchangeapp.util.Constants
import com.example.moneyexchangeapp.workers.SeedDatabaseWorker
import java.util.concurrent.TimeUnit

@Database(
    entities = [LatestExchangeRateResponseModel::class],
    version = Constants.DataBase.VERSION_N0,
    exportSchema = false
)
@TypeConverters(CurrencyRatesConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getCurrencyRateDao(): CurrencyRateDao
}