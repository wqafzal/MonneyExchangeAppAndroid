package com.example.moneyexchangeapp.network.deserializer

import com.example.moneyexchangeapp.data.HistoricalData
import com.example.moneyexchangeapp.data.HistoricalDataResponseModel
import com.example.moneyexchangeapp.data.model.ExchangeRate
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class HistoricalDataResponseSerializer : JsonDeserializer<HistoricalDataResponseModel> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): HistoricalDataResponseModel {
        val base = json?.asJsonObject?.get("base")?.asString ?: ""
        val endDate = json?.asJsonObject?.get("end_date")?.asString ?: ""
        val startDate = json?.asJsonObject?.get("start_date")?.asString ?: ""
        val success = json?.asJsonObject?.get("success")?.asBoolean ?: false
        val timeSeries = json?.asJsonObject?.get("timeseries")?.asBoolean ?: false
        val list = mutableListOf<HistoricalData>()
        if (json?.asJsonObject?.has("rates") == true) {
            val set = json.asJsonObject?.get("rates")?.asJsonObject
            set?.keySet()?.forEach {
                val currency = set.get(it)?.asJsonObject?.let { currencies ->
                    currencies.keySet()?.map {
                        ExchangeRate(
                            it,
                            (currencies.get(it)?.asDouble ?: 0) as Double
                        )
                    }?.toList()

                }?.let { it1 -> HistoricalData(it, currencies = it1) }
                currency?.let { it1 -> list.add(it1) }
            }
        }

        val data = HistoricalDataResponseModel(
            base,
            endDate,
            list,
            startDate,
            success,
            timeSeries
        )

        return data
    }

}