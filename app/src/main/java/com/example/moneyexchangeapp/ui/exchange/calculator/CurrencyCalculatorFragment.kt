package com.example.moneyexchangeapp.ui.exchange.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.adapters.CurrencySymbolsAdapter
import com.example.moneyexchangeapp.base.BaseFragment
import com.example.moneyexchangeapp.data.model.FixerSymbolsResponseModel
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.databinding.FragmentCurrencyCalculatorBinding
import com.example.moneyexchangeapp.network.DataResponseResource
import com.example.moneyexchangeapp.network.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyCalculatorFragment : BaseFragment(), CurrencyCalculatorViewModel.ExchangeEvents {


    private lateinit var viewModel: CurrencyCalculatorViewModel

    private var sourceAdapter = CurrencySymbolsAdapter()
    private var targetAdapter = CurrencySymbolsAdapter()

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
        setSymbolsObserver()
        setExchangeRatesObserver()
    }

    private fun setSymbolsObserver() {
        viewModel.symbolsResponse.observe(viewLifecycleOwner, symbolsObserver)
    }

    private fun setExchangeRatesObserver() {
        viewModel.latestExchangeRates.observe(viewLifecycleOwner, exchangeRatesObserver)
    }

    private var symbolsObserver = Observer<DataResponseResource<FixerSymbolsResponseModel>> {
        when (it.status) {
            Status.SUCCESS -> {
                viewModel.hideLoading()
                it.data?.symbols?.let { list ->
                    sourceAdapter.setItems(list)
                    targetAdapter.setItems(list)
                    binding.spSource.adapter = sourceAdapter
                    binding.spTarget.adapter = targetAdapter
                    if (viewModel.convertFromPosition != -1)
                        binding.spSource.setSelection(viewModel.convertFromPosition)
                    if (viewModel.convertToPosition != -1)
                        binding.spTarget.setSelection(viewModel.convertToPosition)
                    removeSymbolsObserver()
                    viewModel.getLatestRates()
                }
            }
            Status.LOADING -> {
                viewModel.setLoading()
                showLongSnackBar(getString(R.string.initializing))
            }
            Status.ERROR -> {
                viewModel.hideLoading()
                it.message?.let { it1 ->
                    showDialog(getString(R.string.error), it1.plus(getString(R.string.failed_initialization)), getString(R.string.retry), {
                        viewModel.fetchSymbols()
                    }, cancelable = false)
                }
            }
        }
    }

    private var exchangeRatesObserver =
        Observer<DataResponseResource<LatestExchangeRateResponseModel>> {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.hideLoading()
                    removeExchangeRatesError()
                }
                Status.LOADING -> {
                    viewModel.setLoading()
                    showLongSnackBar(getString(R.string.initializing))
                }
                Status.ERROR -> {
                    viewModel.hideLoading()
                    it.message?.let { it1 ->
                        showDialog(getString(R.string.error), it1.plus(getString(R.string.failed_initialization)), getString(R.string.retry), {
                            viewModel.fetchSymbols()
                        }, cancelable = false)
                    }
                }
            }
        }

    private fun removeExchangeRatesError() {
        viewModel.latestExchangeRates.removeObserver(exchangeRatesObserver)
    }

    private fun removeSymbolsObserver() {
        viewModel.symbolsResponse.removeObserver(symbolsObserver)
    }

    override fun swap(fromCurrencyPosition: Int, toCurrencyPosition: Int) {
        binding.spSource.setSelection(fromCurrencyPosition)
        binding.spTarget.setSelection(toCurrencyPosition)
    }

    override fun openDetails() {
        view?.let { view ->
            CurrencyCalculatorFragmentDirections.actionNavCurrencyCalculatorToNavHistory(
                viewModel.convertFrom,
                viewModel.convertTo
            ).let {
                Navigation.findNavController(view)
                    .navigate(it)
            }
        }
    }

}