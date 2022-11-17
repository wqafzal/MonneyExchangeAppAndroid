package com.example.moneyexchangeapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneyexchangeapp.data.model.FixerSymbolsResponseModel

@Dao
interface SymbolsDao {
    @Query("SELECT * FROM FixerSymbolsResponseModel limit 1")
    fun getSymbols(): FixerSymbolsResponseModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(symbols: FixerSymbolsResponseModel)

    @Query("DELETE FROM FixerSymbolsResponseModel")
    suspend fun clearRates()
}