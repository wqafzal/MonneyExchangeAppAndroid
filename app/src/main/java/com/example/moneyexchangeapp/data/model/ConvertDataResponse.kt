package com.example.moneyexchangeapp.data.model

data class ConvertDataResponse(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean,
    val error: Error
)

data class Info(
    val rate: Double,
    val timestamp: Int
)

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)

data class Error(
    val code: Int,
    val info: String,
    val type: String
)
