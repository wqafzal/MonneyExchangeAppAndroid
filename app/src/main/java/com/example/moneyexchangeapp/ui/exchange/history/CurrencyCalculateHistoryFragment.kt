package com.example.moneyexchangeapp.ui.exchange.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.base.BaseFragment
import com.example.moneyexchangeapp.databinding.FragmentCurrencyCaculateHistoryBinding
import com.example.moneyexchangeapp.network.Status
import com.example.moneyexchangeapp.ui.exchange.history.adapters.ExchangeHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyCalculateHistoryFragment : BaseFragment() {

    companion object {
        const val FROM_CURRENCY = "fromCurrency"
        const val TO_CURRENCY = "toCurrency"
    }

    private lateinit var viewModel: CurrencyExchangeHistoryViewModel

    private lateinit var fromCurrency: String
    private lateinit var toCurrency: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CurrencyExchangeHistoryViewModel::class.java]
        arguments?.getString(FROM_CURRENCY)?.let {
            fromCurrency = it
        }
        arguments?.getString(TO_CURRENCY)?.let {
            toCurrency = it
        }
    }

    private lateinit var binding: FragmentCurrencyCaculateHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCurrencyCaculateHistoryBinding.inflate(inflater).let {
            binding = it
            it.viewModel = this@CurrencyCalculateHistoryFragment.viewModel
            it.root
        }
    }

    private var exchangeHistoryAdapter = ExchangeHistoryAdapter()
    private var exchangeHistoryAdapterForCommonCurrencies = ExchangeHistoryAdapter()

    private var famousCurrencies = "USD,PKR,CNY,USD,INR,SAR,RUB,CAD,AUD,CHF"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historicalList.adapter = exchangeHistoryAdapter
        binding.historicalPopularList.adapter = exchangeHistoryAdapterForCommonCurrencies
        getHistoricalDataForSelection()
    }

    private fun getHistoricalDataForSelection() {
        viewModel.getHistoricalData(fromCurrency, fromCurrency.plus(",").plus(toCurrency))
            .observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> {
                        viewModel.loadingHistoricalDataForSelectedOnes.set(true)
                    }
                    Status.SUCCESS -> {
                        viewModel.loadingHistoricalDataForSelectedOnes.set(false)
                        exchangeHistoryAdapter.setItems(it.data?.rates)
                        getHistoricalDataForPastThreeDaysForCommonCurrencies()
                    }
                    Status.ERROR -> {
                        viewModel.loadingHistoricalDataForSelectedOnes.set(false)
                        it.message?.let { it1 ->
                            showDialog(getString(R.string.error), it1, getString(R.string.retry), {
                                getHistoricalDataForSelection()
                            }, onNegative = {
                                view?.let { it2 -> Navigation.findNavController(it2).navigateUp() }
                            }, negativeButtonText = getString(R.string.go_back), cancelable = false)
                        }
                    }

                }
            }
    }

    private fun getHistoricalDataForPastThreeDaysForCommonCurrencies() {
        viewModel.getHistoricalData(fromCurrency, famousCurrencies).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    viewModel.loadingHistoricalDataForCommon.set(true)

                }
                Status.SUCCESS -> {
                    viewModel.loadingHistoricalDataForCommon.set(false)
                    exchangeHistoryAdapterForCommonCurrencies.setItems(it.data?.rates)
                }
                Status.ERROR -> {
                    viewModel.loadingHistoricalDataForCommon.set(false)
                    it.message?.let { it1 ->
                        showDialog(getString(R.string.error), it1, getString(R.string.retry), {
                            getHistoricalDataForPastThreeDaysForCommonCurrencies()
                        }, onNegative = {
                            view?.let { it2 -> Navigation.findNavController(it2).navigateUp() }
                        }, negativeButtonText = getString(R.string.go_back), cancelable = false)
                    }
                }

            }
        }
    }


}