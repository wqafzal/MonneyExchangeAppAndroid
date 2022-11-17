package com.example.moneyexchangeapp.network


import com.google.gson.JsonSyntaxException
import retrofit2.HttpException

class ApiErrorResponse constructor(error: Throwable) {
    private var message = "An error occurred"

    init {
        if (error is HttpException) {
            if (error.code() == 404) {
                this.message = "url not found"
            } else {

                try {

                    val errorJsonString = error.response()
                        ?.errorBody()?.string()
                    if (errorJsonString?.isNotEmpty() == true)

                        ""


                } catch (exception: JsonSyntaxException) {
                    this.message = "Error parsing server response."
                } catch (exception: Exception) {
                    this.message = exception.message ?: this.message
                }

            }


        } else {
            this.message = error.message ?: this.message
        }
    }
}