package com.example.moneyexchangeapp.extensions

import androidx.annotation.RawRes
import com.example.moneyexchangeapp.AndroidApp
import com.example.moneyexchangeapp.util.Constants.DateFormats.DATE_FORMAT
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

fun String.Companion.readRaw(@RawRes resourceId: Int): String {

    val context = AndroidApp.context()
    val inputStream = context.resources.openRawResource(resourceId)
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    var line: String?
    val stringBuilder = StringBuilder()
    line = bufferedReader.readLine()
    while (line != null) {
        stringBuilder.append(line)
        line = bufferedReader.readLine()
    }
    return stringBuilder.toString()
}

fun String.toDate(): Date {
    return SimpleDateFormat(DATE_FORMAT).parse(this)
}