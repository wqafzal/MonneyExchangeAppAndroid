package com.example.moneyexchangeapp.core.extensions

import java.util.*

fun Date.isBeforeToday(): Boolean {
    val toCompare = Calendar.getInstance().let {
        it.time = this
        it
    }
    return Calendar.getInstance().let {
        it.get(Calendar.YEAR) > toCompare.get(Calendar.YEAR) || (it.get(Calendar.YEAR) == toCompare.get(
            Calendar.YEAR
        ) && it.get(Calendar.DAY_OF_YEAR) > toCompare.get(Calendar.DAY_OF_YEAR))
    }
}