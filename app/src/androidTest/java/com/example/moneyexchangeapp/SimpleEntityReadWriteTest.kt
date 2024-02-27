package com.example.moneyexchangeapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.services.events.TimeStamp
import com.example.moneyexchangeapp.core.data.AppDatabase
import com.example.moneyexchangeapp.feature.exchange.data.db.ExchangeRateDao
import com.example.moneyexchangeapp.feature.exchange.data.db.ExchangeRateEntity
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var ratesDao: ExchangeRateDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        ratesDao = db.getExchangeRateDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    private val fakeRate = ExchangeRateEntity(
        base = "USD",
        rate = 1.0,
        timeStamp = TimeStamp.now().seconds.toInt(),
        symbol = "USD"
    )

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {

        ratesDao.insert(fakeRate)
        val recentRecord = ratesDao.getLatestByCurrencyExchangeRate()
        assertEquals(recentRecord, fakeRate)
    }
}