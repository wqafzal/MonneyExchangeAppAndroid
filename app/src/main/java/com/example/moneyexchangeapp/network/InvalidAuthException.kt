package com.example.moneyexchangeapp.network

/**
 * Exception for authentication
 */
class InvalidAuthException(override val message: String?) : Throwable(message)