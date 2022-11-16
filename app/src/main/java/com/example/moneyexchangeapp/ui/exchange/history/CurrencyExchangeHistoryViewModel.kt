package com.example.moneyexchangeapp.ui.exchange.history

import com.example.moneyexchangeapp.AndroidApp
import com.example.moneyexchangeapp.base.BaseViewModel
import com.example.moneyexchangeapp.data.remote.fixerApi.FixerService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeHistoryViewModel @Inject constructor(val fixerService: FixerService) : BaseViewModel() {




}