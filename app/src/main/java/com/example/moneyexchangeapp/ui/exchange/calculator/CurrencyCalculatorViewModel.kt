package com.example.moneyexchangeapp.ui.exchange.calculator

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moneyexchangeapp.BuildConfig
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.base.BaseViewModel
import com.example.moneyexchangeapp.data.model.FixerSymbolsResponseModel
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.data.remote.fixerApi.FixerService
import com.example.moneyexchangeapp.extensions.readRaw
import com.example.moneyexchangeapp.network.ApiResponseResource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CurrencyCalculatorViewModel @Inject constructor(
    private val fixerService: FixerService,
    val gson: Gson
) :
    BaseViewModel() {

    var finalAmount: ObservableField<String> = ObservableField("")
    var isLoading: ObservableField<Boolean> = ObservableField(false)


    private var _symbols = MutableLiveData<ApiResponseResource<FixerSymbolsResponseModel>>()
    var symbolsResponse: LiveData<ApiResponseResource<FixerSymbolsResponseModel>> = _symbols

    private var _latestExchangeRates =
        MutableLiveData<ApiResponseResource<LatestExchangeRateResponseModel>>()
    var latestExchangeRates: LiveData<ApiResponseResource<LatestExchangeRateResponseModel>> =
        _latestExchangeRates

    var events: ExchangeEvents? = null

    private var currentAPiCall: Job? = null
    private var pauseConversion: Boolean = false

    init {
        fetchSymbols()
        getLatestRates()
    }

    private fun getLatestRates() {
        launchApi {
            kotlin.runCatching {
                _latestExchangeRates.postValue(ApiResponseResource.loading())
                if (BuildConfig.useCachedData)
                    getCachedLatestRates()
                else
                    fixerService.getLatestRates()
            }
                .onSuccess {
                    if (it.success)
                        _latestExchangeRates.postValue(ApiResponseResource.success(it))
                    else _latestExchangeRates.postValue(it.error?.info?.let { it1 ->
                        ApiResponseResource.error(
                            it1
                        )
                    })
                }
                .onFailure {
                    _latestExchangeRates.postValue(ApiResponseResource.error(onHandleError(it)))
                }
        }
    }

    fun fetchSymbols() {
        launchApi {
            kotlin.runCatching {
                isLoading.set(true)
                _symbols.postValue(ApiResponseResource.loading())
                if (BuildConfig.useCachedData)
                    getCachedSymbols()
                else fixerService.getSymbols()
            }.onSuccess {
                isLoading.set(false)
                if (it.success)
                    _symbols.postValue(ApiResponseResource.success(it))
                else _symbols.postValue(ApiResponseResource.error("Un able to fetch symbols"))
            }.onFailure {
                isLoading.set(false)
                val message = onHandleError(it)
                _symbols.postValue(ApiResponseResource.error(message))
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

    var convertTo = "USD"
    var convertFrom = "EUR"

    fun onConvertToSelect(position: Int) {
        _symbols.value?.data?.symbols?.get(position)?.let {
            convertTo = it.currencySymbol
        }
        println("position $position")
        convertAmount()
    }


    private fun convertAmount() {
        if (amount.isEmpty() || pauseConversion)
            return
        currentAPiCall?.cancel()
        currentAPiCall = launchApi {
            kotlin.runCatching {
                isLoading.set(true)
                fixerService.getConvertedResult(
                    convertTo = convertTo,
                    convertFrom = convertFrom,
                    amount = amount
                )
            }.onSuccess {
                isLoading.set(false)
                if (it.success) {
                    updateConversion(it.result)
                } else {
                    events?.onError(it.error.info)
                }
            }.onFailure {
                isLoading.set(false)
                val errorMessage = onHandleError(it)
                if (errorMessage.contains("Your limit exceeds.") && _latestExchangeRates.value?.data?.rates?.isNotEmpty() == true) {
                    updateConversionFromLatestRates()
                } else
                    if (errorMessage.lowercase(Locale.ROOT).contains("coroutine").not())
                        events?.onError(errorMessage)
            }
        }
    }

    private fun updateConversionFromLatestRates() {
        _latestExchangeRates.value?.data?.rates?.let { rateList ->
            val amountInEuros =
                amount.toDouble().div(rateList.first { it.symbol == convertFrom }.rate)
            updateConversion(amountInEuros.times(rateList.first { it.symbol == convertTo }.rate))
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
        _symbols.value?.data?.symbols?.get(position)?.let {
            convertFrom = it.currencySymbol
        }
        convertAmount()
    }

    fun swapCurrencies() {
        _symbols.value?.data?.symbols?.let { list ->
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

    private var amount: String = "1"

    interface ExchangeEvents {
        fun onError(message: String)
        fun swap(fromCurrencyPosition: Int, toCurrencyPosition: Int)
        fun openDetails()
    }

}