package com.example.moneyexchangeapp.ui.exchange.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.adapters.CurrencySymbolsAdapter
import com.example.moneyexchangeapp.base.BaseFragment
import com.example.moneyexchangeapp.databinding.FragmentCurrencyCalculatorBinding
import com.example.moneyexchangeapp.network.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyCalculatorFragment : BaseFragment(), CurrencyCalculatorViewModel.ExchangeEvents {

    companion object {
    }

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
    }

    private fun setSymbolsObserver() {
        viewModel.symbolsResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.symbols?.let { list ->
                        sourceAdapter.setItems(list)
                        targetAdapter.setItems(list)
                        binding.spSource.adapter = sourceAdapter
                        binding.spTarget.adapter = targetAdapter
                    }
                }
                Status.LOADING -> {
                    showLongSnackBar("Loading Currencies list...")
                }
                Status.ERROR -> {
                    it.message?.let { it1 ->
                        showLongSnackBar(it1.plus(getString(R.string.reloadSymbols))) {
                            viewModel.fetchSymbols()
                        }
                    }
                }
            }
        }
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


    override fun onError(message: String) {
        showLongSnackBar(message)
    }

}