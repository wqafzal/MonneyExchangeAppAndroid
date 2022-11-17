package com.example.moneyexchangeapp.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.base.BaseRepository
import com.example.moneyexchangeapp.data.local.room.dao.CurrencyRateDao
import com.example.moneyexchangeapp.data.local.room.dao.SymbolsDao
import com.example.moneyexchangeapp.data.model.FixerSymbolsResponseModel
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.data.remote.fixerApi.FixerService
import com.example.moneyexchangeapp.extensions.beforeToday
import com.example.moneyexchangeapp.extensions.readRaw
import com.example.moneyexchangeapp.extensions.toDate
import com.example.moneyexchangeapp.network.DataResponseResource
import com.example.moneyexchangeapp.util.CoroutineHelper
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val fixerService: FixerService,
    private val currencyRateDao: CurrencyRateDao,
    private val symbolsDao: SymbolsDao,
    val gson: Gson,
    @ApplicationContext
    val context: Context
) : BaseRepository() {
    private suspend fun getCurrentRates(): LatestExchangeRateResponseModel {
        return fixerService.getLatestRates()
    }

    private suspend fun getSymbols(): FixerSymbolsResponseModel {
        return fixerService.getSymbols()
    }

    var symbolsDataLiveData = MutableLiveData<DataResponseResource<FixerSymbolsResponseModel>>()
    var storedCurrencyRatesLiveData =
        MutableLiveData<DataResponseResource<LatestExchangeRateResponseModel>>()

    fun getStoredCurrencyRatesData() {
        CoroutineHelper.io {
            kotlin.runCatching {
                storedCurrencyRatesLiveData.postValue(DataResponseResource.loading())
                var currencyRates = currencyRateDao.getLatestByCurrencyExchangeRate()

                if (currencyRates != null && currencyRates.date.toDate().beforeToday().not())
                    storedCurrencyRatesLiveData.postValue(DataResponseResource.success(currencyRates))
                else {
                    currencyRates = getCurrentRates()
                    if (currencyRates.success) {
                        currencyRateDao.clearRecords()
                        currencyRateDao.insert(currencyRates)
                        storedCurrencyRatesLiveData.postValue(
                            DataResponseResource.success(
                                currencyRates
                            )
                        )
                    } else
                        storedCurrencyRatesLiveData.postValue(
                            DataResponseResource.error(
                                context.getString(
                                    R.string.some_error
                                )
                            )
                        )
                }
            }.onFailure {
                storedCurrencyRatesLiveData.postValue(DataResponseResource.error(onHandleError(it)))
            }
        }
    }

    fun getStoredSymbols() {
        CoroutineHelper.io {
            kotlin.runCatching {
                symbolsDataLiveData.postValue(DataResponseResource.loading())
                var symbols = symbolsDao.getSymbols()
                if (symbols != null)
                    symbolsDataLiveData.postValue(DataResponseResource.success(symbols))
                else {
                    symbols = getSymbols()
                    if (symbols.success) {
                        symbolsDao.insert(symbols)
                        symbolsDataLiveData.postValue(DataResponseResource.success(symbols))
                    } else
                        symbolsDataLiveData.postValue(DataResponseResource.error(context.getString(R.string.some_error)))
                }
            }.onFailure {
                symbolsDataLiveData.postValue(DataResponseResource.error(onHandleError(it)))
            }
        }
    }


    private fun getCachedLatestRates(): LatestExchangeRateResponseModel {
        return gson.fromJson(
            String.readRaw(R.raw.base_rates),
            LatestExchangeRateResponseModel::class.java
        )
    }

    private fun getCachedSymbols(): FixerSymbolsResponseModel {
        return gson.fromJson(String.readRaw(R.raw.symbols), FixerSymbolsResponseModel::class.java)
    }


}