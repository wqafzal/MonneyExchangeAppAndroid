package com.example.moneyexchangeapp.network.deserializer

import com.example.moneyexchangeapp.data.model.ExchangeRate
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ExchangeRateResponseModelDeserializer : JsonDeserializer<LatestExchangeRateResponseModel> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LatestExchangeRateResponseModel {
        val listOfCountries = mutableListOf<ExchangeRate>()
        kotlin.runCatching {
            if (json?.asJsonObject?.has("rates") == true) {
                val countrySet = json.asJsonObject.get("rates")?.asJsonObject
                countrySet?.keySet()?.let { keys ->
                    keys.forEach { key ->
                        listOfCountries.add(
                            ExchangeRate(
                                symbol = key,
                                rate = countrySet.get(key).asDouble
                            )
                        )
                    }
                }
            }
        }
        return LatestExchangeRateResponseModel(
            base = json?.asJsonObject?.get("base")?.asString ?: "",
            rates = listOfCountries,
            timestamp = json?.asJsonObject?.get("timestamp")?.asInt ?: 0
        )
    }
}