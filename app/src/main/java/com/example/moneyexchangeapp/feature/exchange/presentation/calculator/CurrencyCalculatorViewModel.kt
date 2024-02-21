package com.example.moneyexchangeapp.feature.exchange.presentation.calculator

import com.example.moneyexchangeapp.base.BaseViewModel
import com.example.moneyexchangeapp.data.model.ExchangeRate
import com.example.moneyexchangeapp.repository.AppRepository
import com.example.moneyexchangeapp.core.util.ConversionUtils
import com.example.moneyexchangeapp.util.CoroutineHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyCalculatorViewModel @Inject constructor(
    private val repository: AppRepository
) :
    BaseViewModel() {

    var latestExchangeRates = repository.exchangeRatesObserver
    var dataState = repository.dataState

    var events: ExchangeEvents? = null

    private var amount: String = "1"

    fun getLatestRates() {
        CoroutineHelper.io {
            repository.getStoredCurrencyRatesData()
        }
    }

    var convertFrom = ""

    var convertFromPosition = -1

    private fun convertAmount() {
        if (amount.isEmpty() || convertFrom.isEmpty())
            return
        latestExchangeRates.value?.rates?.let {
            CoroutineHelper.io {
                val list = ConversionUtils.updateConversionFromLatestRates(
                    it,
                    convertFrom,
                    amount.toDouble()
                )
                CoroutineHelper.main {
                    events?.updateList(list)
                }
            }
        }
    }

    fun onConvertFromSelect(position: Int) {
        convertFromPosition = position
        latestExchangeRates.value?.rates?.get(position)?.let {
            convertFrom = it.symbol
        }
        convertAmount()
    }

    fun onTextChange(text: CharSequence) {
        amount = text.toString()
        convertAmount()
    }


    interface ExchangeEvents {
        fun updateList(list: List<ExchangeRate>)
    }

}