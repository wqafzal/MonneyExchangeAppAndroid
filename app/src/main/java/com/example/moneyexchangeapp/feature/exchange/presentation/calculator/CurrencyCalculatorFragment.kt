package com.example.moneyexchangeapp.feature.exchange.presentation.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moneyexchangeapp.core.base.BaseFragment
import com.example.moneyexchangeapp.databinding.FragmentCurrencyCalculatorBinding
import com.example.moneyexchangeapp.feature.exchange.domain.model.ExchangeRate
import com.example.moneyexchangeapp.feature.exchange.presentation.calculator.adapters.ConvertedAmountListAdapter
import com.example.moneyexchangeapp.feature.exchange.presentation.calculator.adapters.CurrencySymbolsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyCalculatorFragment : BaseFragment() {


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
        binding.resultListView.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        binding.resultListView.adapter = convertedAmountListAdapter
        setSymbolsObserver()
        setConversionResultsObserver()
    }

    private fun setSymbolsObserver() {
        viewModel.usdExchangeRates.observe(viewLifecycleOwner) {
            populateSymbols(rates = it)
        }
    }

    private fun setConversionResultsObserver() {
        viewModel.conversionResults.observe(viewLifecycleOwner) {
            convertedAmountListAdapter.setItems(it)
        }
    }

    private fun populateSymbols(rates:List<ExchangeRate>) {
        sourceAdapter.setItems(rates)
        binding.spSource.adapter = sourceAdapter
        if (viewModel.convertFromPosition != -1)
            binding.spSource.setSelection(viewModel.convertFromPosition)
    }
}