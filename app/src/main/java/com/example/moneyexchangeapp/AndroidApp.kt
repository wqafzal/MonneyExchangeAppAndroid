package com.example.moneyexchangeapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidApp : Application(){
    init {
        instance = this
    }

    companion object{
        lateinit var instance: AndroidApp
        fun context() : Context {
            return instance.applicationContext
        }
    }
}