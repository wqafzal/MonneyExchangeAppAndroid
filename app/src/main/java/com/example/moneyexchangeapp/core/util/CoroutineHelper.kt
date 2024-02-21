package com.example.moneyexchangeapp.core.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object CoroutineHelper {

    fun main(work: suspend () -> Unit) =
        CoroutineScope(Dispatchers.Main).launch {
            work.invoke()
        }

    fun io(work: suspend () -> Unit) =
        CoroutineScope(Dispatchers.IO).launch {
            work.invoke()
        }


}