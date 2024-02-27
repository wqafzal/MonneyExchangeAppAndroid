package com.example.moneyexchangeapp.core.base

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    var isLoading: ObservableField<Boolean> = ObservableField(false)


    fun setLoading() {
        isLoading.set(true)
    }

    fun hideLoading() {
        isLoading.set(false)
    }

}