package com.example.moneyexchangeapp.ui.exchange.history

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.example.moneyexchangeapp.base.BaseViewModel
import com.example.moneyexchangeapp.data.HistoricalDataResponseModel
import com.example.moneyexchangeapp.network.DataResponseResource
import com.example.moneyexchangeapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeHistoryViewModel @Inject constructor(
    private val repository: AppRepository
) :
    BaseViewModel() {

    var loadingHistoricalDataForSelectedOnes = ObservableField(false)
    var loadingHistoricalDataForCommon = ObservableField(false)

    fun getHistoricalData(
        fromCurrency: String,
        toCurrency: String
    ): LiveData<DataResponseResource<HistoricalDataResponseModel>> {
        return repository.getHistoricalData(fromCurrency, toCurrency)
    }

}