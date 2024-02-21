package com.example.moneyexchangeapp.feature.exchange.presentation.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.core.base.BaseFragment
import com.example.moneyexchangeapp.data.model.ExchangeRate
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.databinding.FragmentCurrencyCalculatorBinding
import com.example.moneyexchangeapp.core.data.network.DataResponseResource
import com.example.moneyexchangeapp.core.data.network.Status
import com.example.moneyexchangeapp.feature.exchange.presentation.calculator.adapters.ConvertedAmountListAdapter
import com.example.moneyexchangeapp.feature.exchange.presentation.calculator.adapters.CurrencySymbolsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyCalculatorFragment : BaseFragment(), CurrencyCalculatorViewModel.ExchangeEvents {


    private lateinit var viewModel: CurrencyCalculatorViewModel

    private var sourceAdapter = CurrencySymbolsAdapter()
    private var convertedAmountListAdapter = ConvertedAmountListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CurrencyCalculatorViewModel::class.java]
    }

    private lateinit var binding: FragmentCurrencyCalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCurrencyCalculatorBinding.inflate(LayoutInflater.from(context))
            .apply {
                binding = this
                this.viewModel = this@CurrencyCalculatorFragment.viewModel
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.events = this
        binding.resultListView.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        binding.resultListView.adapter = convertedAmountListAdapter
        setSymbolsObserver()
        setDataStateObserver()
    }

    private fun setSymbolsObserver() {
        viewModel.latestExchangeRates.observe(viewLifecycleOwner) {
            it?.rates?.let { it1 -> populateSymbols(it1) }
        }
    }

    private fun populateSymbols(rates:List<ExchangeRate>) {
        sourceAdapter.setItems(rates)
        binding.spSource.adapter = sourceAdapter
        if (viewModel.convertFromPosition != -1)
            binding.spSource.setSelection(viewModel.convertFromPosition)
    }

    private fun setDataStateObserver() {
        viewModel.dataState.observe(viewLifecycleOwner, dataStateObserver)
    }

    private var dataStateObserver =
        Observer<DataResponseResource<LatestExchangeRateResponseModel>> {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.hideLoading()
                }
                Status.LOADING -> {
                    viewModel.setLoading()
                    showLongSnackBar(getString(R.string.initializing))
                }
                Status.ERROR -> {
                    viewModel.hideLoading()
                    it.message?.let { it1 ->
                        showDialog(getString(R.string.error), it1.plus(getString(R.string.failed_initialization)), getString(R.string.retry), {
                            viewModel.getLatestRates()
                        }, cancelable = false)
                    }
                }
            }
        }

    override fun updateList(list: List<ExchangeRate>) {
        convertedAmountListAdapter.setItems(list)
    }
}