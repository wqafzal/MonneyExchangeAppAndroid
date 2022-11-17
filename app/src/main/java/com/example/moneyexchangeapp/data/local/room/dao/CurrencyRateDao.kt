package com.example.moneyexchangeapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel

@Dao
interface CurrencyRateDao {

    @Query("SELECT * FROM LatestExchangeRateResponseModel order by timestamp DESC limit 1 ")
    fun getLatestByCurrencyExchangeRate(): LatestExchangeRateResponseModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(latestExchangeRateResponseModel: LatestExchangeRateResponseModel)

    @Query("DELETE FROM LatestExchangeRateResponseModel")
    suspend fun clearRecords()
}