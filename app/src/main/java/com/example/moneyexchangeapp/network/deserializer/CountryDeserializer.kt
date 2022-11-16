package com.example.moneyexchangeapp.network.deserializer

import com.example.moneyexchangeapp.data.model.Country
import com.example.moneyexchangeapp.data.model.FixerSymbolsResponseModel
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CountryDeserializer : JsonDeserializer<FixerSymbolsResponseModel> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): FixerSymbolsResponseModel {
        val listOfCountries = mutableListOf<Country>()
        kotlin.runCatching {
            if (json?.asJsonObject?.has("symbols") == true) {
                val countrySet = json.asJsonObject.get("symbols")?.asJsonObject
                countrySet?.keySet()?.let { keys ->
                    keys.forEach { key ->
                        listOfCountries.add(Country(name = countrySet.get(key).asString, currencySymbol = key))
                    }
                }
            }
        }
        return FixerSymbolsResponseModel(
            json?.asJsonObject?.get("success")?.asBoolean ?: false,
            symbols = listOfCountries
        )
    }
}