package com.example.moneyexchangeapp.feature.exchange.presentation.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moneyexchangeapp.core.base.BaseViewModel
import com.example.moneyexchangeapp.feature.exchange.domain.ExchangeRepository
import com.example.moneyexchangeapp.feature.exchange.domain.model.ExchangeRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyCalculatorViewModel @Inject constructor(
    private val repository: ExchangeRepository
) : BaseViewModel() {

    private val _usdExchangeRates = MutableLiveData<List<ExchangeRate>>()
    val usdExchangeRates: LiveData<List<ExchangeRate>> = _usdExchangeRates

    private val _conversionResults = MutableLiveData<List<ExchangeRate>>()
    val conversionResults: LiveData<List<ExchangeRate>> = _usdExchangeRates

    init {
        viewModelScope.launch {
            repository.getSymbols().collect {
                _usdExchangeRates.value = it
            }
        }
    }

    private var amount: String = "1"

    private var convertFrom = ""

    var convertFromPosition = -1

    private fun convertAmount() {
        if (amount.isEmpty() || convertFrom.isEmpty()) {
            return
        }
        viewModelScope.launch {
            val rates = repository.getLatestExchangeRates(
                base = convertFrom,
                amount = amount.toDouble()
            )
            _conversionResults.value = rates
        }
    }

    fun onConvertFromSelect(position: Int) {
        convertFromPosition = position
        usdExchangeRates.value?.get(position)?.let {
            convertFrom = it.symbol
        }
        convertAmount()
    }

    fun onTextChange(text: CharSequence) {
        amount = text.toString()
        convertAmount()
    }

}