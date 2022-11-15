package com.example.moneyexchangeapp.ui.exchange.calculator

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.base.BaseFragment

class CurrencyCalculatorFragment : BaseFragment() {

    companion object {
        fun newInstance() = CurrencyCalculatorFragment()
    }

    private lateinit var viewModel: CurrencyCalculatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_calculator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CurrencyCalculatorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}