package com.example.moneyexchangeapp.base

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyexchangeapp.AndroidApp
import com.google.gson.stream.MalformedJsonException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel(app:AndroidApp) : AndroidViewModel(app) {
    /**
     * This Method is called from View Model inca se HTTP code is greater than 299
     * another possible flow of invoking is if any exception occurs in try Running Code Block
     * due to Data Parsing or any other exception
     * */
    fun onHandleError(error: Throwable): String {
        var message = error.message ?: "Unknown error"
        when (error) {
            is UnknownHostException -> {
                message = "Failed to connect to server. Check you internet connection."
            }
            is SocketTimeoutException -> {
                message = "Failed to connect to server. Timeout."
            }
            is IOException -> {
                message = "Failed to access resource."
            }
//            is InvalidAuthException -> {
//                message= "In Valid Session data."
//            }
            is MalformedJsonException -> {
                message= "Error parsing data."
            }
        }
        return message
    }

    fun launchApi(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(dispatcher, block = block)

}