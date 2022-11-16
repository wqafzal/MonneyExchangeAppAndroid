package com.example.moneyexchangeapp.ui.exchange.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.moneyexchangeapp.base.BaseFragment
import com.example.moneyexchangeapp.databinding.FragmentCurrencyCaculateHistoryBinding
import com.example.moneyexchangeapp.network.Status
import com.example.moneyexchangeapp.ui.exchange.history.adapters.ExchangeHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyCalculateHistoryFragment : BaseFragment() {

    companion object {
        fun newInstance() = CurrencyCalculateHistoryFragment()
        const val FROM_CURRENCY = "fromCurrency"
        const val TO_CURRENCY = "toCurrency"
    }

    private lateinit var viewModel: CurrencyExchangeHistoryViewModel

    lateinit var fromCurrency: String
    lateinit var toCurrency: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CurrencyExchangeHistoryViewModel::class.java]
        fromCurrency = arguments?.getString(FROM_CURRENCY) ?: "EUR"
        toCurrency = arguments?.getString(TO_CURRENCY) ?: "USD"
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

    var exchangeHistoryAdapter = ExchangeHistoryAdapter()
    var exchangeHistoryAdapterForCommonCurrencies = ExchangeHistoryAdapter()

    var famousCurrencies = "USD,PKR,CNY,USD,INR,SAR,RUB,CAD,AUD,CHF"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historicalList.adapter = exchangeHistoryAdapter
        binding.historicalPopularList.adapter = exchangeHistoryAdapterForCommonCurrencies
        viewModel.getHistoricalData(fromCurrency, toCurrency).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    exchangeHistoryAdapter.setItems(it.data?.rates)
                }
                Status.ERROR -> {
                    it.message?.let { it1 -> showLongSnackbar(it1) }
                }

            }
        }
        viewModel.getHistoricalData(fromCurrency, famousCurrencies).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    exchangeHistoryAdapterForCommonCurrencies.setItems(it.data?.rates)
                }
                Status.ERROR -> {
                    it.message?.let { it1 -> showLongSnackbar(it1) }
                }

            }
        }
    }


}