package com.example.moneyexchangeapp.feature.exchange.data.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ExchangeRateResponseDeserializer : JsonDeserializer<LatestExchangeRateResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LatestExchangeRateResponse {
        val listOfCountries = mutableListOf<ExchangeRateDto>()
        kotlin.runCatching {
            if (json?.asJsonObject?.has("rates") == true) {
                val countrySet = json.asJsonObject.get("rates")?.asJsonObject
                countrySet?.keySet()?.let { keys ->
                    keys.forEach { key ->
                        listOfCountries.add(
                            ExchangeRateDto(
                                symbol = key,
                                rate = countrySet.get(key).asDouble
                            )
                        )
                    }
                }
            }
        }
        return LatestExchangeRateResponse(
            base = json?.asJsonObject?.get("base")?.asString ?: "",
            rates = listOfCountries,
            timestamp = json?.asJsonObject?.get("timestamp")?.asInt ?: 0
        )
    }
}