package com.example.moneyexchangeapp.ui.exchange.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moneyexchangeapp.R

class CurrencyCaculateHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = CurrencyCaculateHistoryFragment()
    }

    private lateinit var viewModel: CurrencyExchangeHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_caculate_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[CurrencyExchangeHistoryViewModel::class.java]
        // TODO: Use the ViewModel
    }

}