package com.example.moneyexchangeapp.ui.exchange.history

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moneyexchangeapp.BuildConfig
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.base.BaseViewModel
import com.example.moneyexchangeapp.data.HistoricalDataResponseModel
import com.example.moneyexchangeapp.data.remote.fixerApi.FixerService
import com.example.moneyexchangeapp.extensions.readRaw
import com.example.moneyexchangeapp.network.DataResponseResource
import com.example.moneyexchangeapp.util.Constants
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeHistoryViewModel @Inject constructor(
    private val fixerService: FixerService,
    private val gson: Gson
) :
    BaseViewModel() {

    companion object{
    }

    var loadingHistoricalDataForSelectedOnes = ObservableField(false)
    var loadingHistoricalDataForCommon = ObservableField(false)

    fun getHistoricalData(
        fromCurrency: String,
        toCurrency: String
    ): LiveData<DataResponseResource<HistoricalDataResponseModel>> {
        val startDate = getDateFromXDaysAgo(2)
        val endDate = getDateFromXDaysAgo(0)
        val response = MutableLiveData<DataResponseResource<HistoricalDataResponseModel>>(
            DataResponseResource.loading()
        )
        launchApi {
            kotlin.runCatching {
                if (BuildConfig.useCachedData)
                    getCachedHistory()
                else
                    fixerService.getHistoricalData(
                        startDate,
                        endDate,
                        fromCurrency,
                        toCurrency,
                    )
            }.onSuccess {
                response.postValue(DataResponseResource.success(it))
            }.onFailure {
                response.postValue(DataResponseResource.error(onHandleError(it)))
            }
        }
        return response
    }

    private fun getCachedHistory(): HistoricalDataResponseModel {
        return gson.fromJson(
            String.readRaw(R.raw.time_series),
            HistoricalDataResponseModel::class.java
        )
    }

    private fun getDateFromXDaysAgo(daysAgo: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -daysAgo)
        val simpleDateFormat = SimpleDateFormat(Constants.DateFormats.DATE_FORMAT, Locale.getDefault())

        return simpleDateFormat.format(calendar.time)
    }


}