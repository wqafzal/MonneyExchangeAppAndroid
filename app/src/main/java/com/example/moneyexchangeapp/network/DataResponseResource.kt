package com.example.moneyexchangeapp.network

import androidx.annotation.Nullable
import com.example.moneyexchangeapp.network.Status.ERROR
import com.example.moneyexchangeapp.network.Status.LOADING
import com.example.moneyexchangeapp.network.Status.NO_MORE_ITEM
import com.example.moneyexchangeapp.network.Status.SUCCESS


class DataResponseResource<T> private constructor(
    val status: Int,
    @param:Nullable @field:Nullable val message: String?
) {

    var data: T? = null


    constructor(status: Int, response: T, message: String?) : this(status, message) {
        this.data = response
    }


    companion object {

        fun <T> success(data: T): DataResponseResource<T> {
            return DataResponseResource(SUCCESS, data, "success")
        }

        fun <T> error(msg: String): DataResponseResource<T> {
            return DataResponseResource(ERROR, msg)
        }

        fun <T> loading(): DataResponseResource<T> {
            return DataResponseResource(LOADING, "loading")
        }

    }

}