package com.example.moneyexchangeapp.extensions

import androidx.annotation.RawRes
import com.example.moneyexchangeapp.AndroidApp
import java.io.BufferedReader
import java.io.InputStreamReader

fun String.Companion.readRaw(@RawRes resourceId: Int): String {

    val context = AndroidApp.context()
    val inputStream = context.resources.openRawResource(resourceId)
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    var line: String? = null
    val stringBuilder = StringBuilder()
    line = bufferedReader.readLine()
    while (line != null) {
        stringBuilder.append(line)
        line = bufferedReader.readLine()
    }
    return stringBuilder.toString()
}