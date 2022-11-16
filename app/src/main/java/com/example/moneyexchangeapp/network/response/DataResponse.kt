package com.example.moneyexchangeapp.network.response

import com.google.gson.annotations.SerializedName

open class DataResponse<T> {
    @SerializedName("success")
    val success: Boolean = false

    @SerializedName("message")
    val message: String = ""

    @SerializedName("data")
    var items: List<T> = ArrayList()
    val totalElements: Int = 0
    val pageCount: Int = 0
    val perPage: Int = 0
    val page: Int = 0
}