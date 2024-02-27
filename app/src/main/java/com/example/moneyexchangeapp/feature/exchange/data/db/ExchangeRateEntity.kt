package com.example.moneyexchangeapp.feature.exchange.data.db

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "exchange_rate", primaryKeys = ["base", "symbol"])
data class ExchangeRateEntity(
    val base: String,
    val symbol: String,
    val rate: Double,
    @ColumnInfo(name = "time_stamp")
    val timeStamp: Int
)

@Dao
interface ExchangeRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rows: ExchangeRateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rows: List<ExchangeRateEntity>)

    @Query("SELECT * FROM exchange_rate WHERE :base = base")
    fun getLatestRates(base: String): Flow<List<ExchangeRateEntity>>

    @Query("DELETE FROM exchange_rate")
    suspend fun deleteAll()

    @Query("SELECT * FROM exchange_rate order by time_stamp DESC limit 1 ")
    fun getLatestByCurrencyExchangeRate(): ExchangeRateEntity
}