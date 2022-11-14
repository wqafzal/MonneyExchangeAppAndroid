package com.example.moneyexchangeapp

import android.app.Application
import android.content.Context

class AndroidApp : Application(){
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object{
        lateinit var instance: AndroidApp
        fun context() : Context {
            return instance.applicationContext
        }
    }
}