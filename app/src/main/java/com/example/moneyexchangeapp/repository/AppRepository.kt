package com.example.moneyexchangeapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.moneyexchangeapp.BuildConfig
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.core.base.BaseRepository
import com.example.moneyexchangeapp.data.HistoricalDataResponseModel
import androidx.work.ListenableWorker
import com.example.moneyexchangeapp.base.BaseRepository
import com.example.moneyexchangeapp.data.local.room.dao.CurrencyRateDao
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.data.remote.fixerApi.FixerService
import com.example.moneyexchangeapp.core.extensions.isBeforeToday
import com.example.moneyexchangeapp.core.extensions.readRaw
import com.example.moneyexchangeapp.core.extensions.toDate
import com.example.moneyexchangeapp.core.data.network.DataResponseResource
import com.example.moneyexchangeapp.core.util.Constants
import com.example.moneyexchangeapp.core.util.CoroutineHelper
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import com.example.moneyexchangeapp.data.remote.exchangeRateApi.ExchangeRatesService
import com.example.moneyexchangeapp.network.DataResponseResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val exchangeRatesService: ExchangeRatesService,
    private val currencyRateDao: CurrencyRateDao,
) : BaseRepository() {
    private suspend fun getCurrentRates(): LatestExchangeRateResponseModel {
        return exchangeRatesService.getLatestRates()
    }

    var dataState =
        MutableLiveData<DataResponseResource<LatestExchangeRateResponseModel>>()

    var exchangeRatesObserver = currencyRateDao.getLatestByCurrencyExchangeRateObservable()

    suspend fun getStoredCurrencyRatesData(): ListenableWorker.Result {
        kotlin.runCatching {
            dataState.postValue(DataResponseResource.loading())
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