package com.example.moneyexchangeapp.feature.exchange.di

import com.example.moneyexchangeapp.core.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/***
 * Note: the installing component is [ViewModelComponent]
 * to make these dependencies available only to [androidx.lifecycle.ViewModel] classes
 */
@InstallIn(ViewModelComponent::class)
@Module
abstract class ExchangeModule {
    @Provides
    fun getExchangeRateDao(appDatabase: AppDatabase) = appDatabase.getExchangeRateDao()
}