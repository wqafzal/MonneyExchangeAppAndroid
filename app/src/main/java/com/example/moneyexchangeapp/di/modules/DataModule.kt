package com.example.moneyexchangeapp.di.modules

import com.example.moneyexchangeapp.data.remote.fixerApi.FixerService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun getFixerService(retrofit: Retrofit): FixerService {
        return retrofit.create(FixerService::class.java)
    }
}