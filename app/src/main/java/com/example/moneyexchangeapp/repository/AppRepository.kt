package com.example.moneyexchangeapp.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.work.ListenableWorker
import com.example.moneyexchangeapp.base.BaseRepository
import com.example.moneyexchangeapp.data.local.room.dao.CurrencyRateDao
import com.example.moneyexchangeapp.data.model.ExchangeRate
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.data.remote.exchangeRateApi.ExchangeRatesService
import com.example.moneyexchangeapp.network.DataResponseResource
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val exchangeRatesService: ExchangeRatesService,
    private val currencyRateDao: CurrencyRateDao,
    val gson: Gson,
    @ApplicationContext val context: Context
) : BaseRepository() {
    private suspend fun getCurrentRates(): LatestExchangeRateResponseModel {
        return exchangeRatesService.getLatestRates()
    }

    var dataState =
        MutableLiveData<DataResponseResource<LatestExchangeRateResponseModel>>()

    var exchangeRatesObserver = currencyRateDao.getLatestByCurrencyExchangeRate()

    suspend fun getStoredCurrencyRatesData(): ListenableWorker.Result {
        kotlin.runCatching {
            val currencyRates: LatestExchangeRateResponseModel = getCurrentRates()
            currencyRateDao.clearRecords()
            currencyRateDao.insert(currencyRates)
            dataState.postValue(
                DataResponseResource.success(
                    currencyRates
                )
            )

        }.onFailure {
            dataState.postValue(DataResponseResource.error(onHandleError(it)))
            return ListenableWorker.Result.failure()
        }
        return ListenableWorker.Result.success()
    }


}