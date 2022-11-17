package com.example.moneyexchangeapp.ui.exchange.calculator

import androidx.databinding.ObservableField
import com.example.moneyexchangeapp.base.BaseViewModel
import com.example.moneyexchangeapp.data.model.ExchangeRate
import com.example.moneyexchangeapp.repository.AppRepository
import com.example.moneyexchangeapp.ui.exchange.history.adapters.ExchangeHistoryAdapter
import com.example.moneyexchangeapp.util.ConversionUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class CurrencyCalculatorViewModel @Inject constructor(
    private val repository: AppRepository
) :
    BaseViewModel() {

    var finalAmount: ObservableField<String> = ObservableField("1")

    var symbolsResponse = repository.symbolsDataLiveData

    var latestExchangeRates = repository.storedCurrencyRatesLiveData

    var events: ExchangeEvents? = null

    private var pauseConversion: Boolean = false

    private var amount: String = "1"

    init {
        fetchSymbols()
    }

    fun getLatestRates() {
        repository.getStoredCurrencyRatesData()
    }

    fun fetchSymbols() {
        repository.getStoredSymbols()
    }

    var convertTo = "USD"
    var convertFrom = "EUR"

    fun onConvertToSelect(position: Int) {
        symbolsResponse.value?.data?.symbols?.get(position)?.let {
            convertTo = it.currencySymbol
        }
        convertAmount()
    }

    private fun convertAmount() {
        if (amount.isEmpty() || pauseConversion)
            return
        latestExchangeRates.value?.data?.rates?.let {
            updateConversion(ConversionUtils.updateConversionFromLatestRates(it, convertFrom, convertTo, amount.toDouble()))
        }
    }

    private fun updateConversion(result: Double) {
        val decimalFormat = DecimalFormat("#.####")
        decimalFormat.roundingMode = RoundingMode.FLOOR
        finalAmount.set(decimalFormat.format(result))
    }

    fun openDetails() {
        events?.openDetails()
    }

    fun onConvertFromSelect(position: Int) {
        symbolsResponse.value?.data?.symbols?.get(position)?.let {
            convertFrom = it.currencySymbol
        }
        convertAmount()
    }

    fun swapCurrencies() {
        symbolsResponse.value?.data?.symbols?.let { list ->
            if (list.isNotEmpty()) {
                pauseConversion = true
                events?.swap(
                    list.indexOfFirst { convertTo == it.currencySymbol },
                    list.indexOfFirst { convertFrom == it.currencySymbol })
                pauseConversion = false
                convertAmount()
            }
        }
    }

    fun onTextChange(text: CharSequence) {
        amount = text.toString()
        convertAmount()
    }


    interface ExchangeEvents {
        fun swap(fromCurrencyPosition: Int, toCurrencyPosition: Int)
        fun openDetails()
    }

}