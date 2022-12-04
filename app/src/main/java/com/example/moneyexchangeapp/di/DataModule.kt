package com.example.moneyexchangeapp.di

import android.content.Context
import androidx.room.Room
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.moneyexchangeapp.BuildConfig
import com.example.moneyexchangeapp.data.local.room.db.AppDatabase
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.data.remote.exchangeRateApi.ExchangeRatesService
import com.example.moneyexchangeapp.network.deserializer.ExchangeRateResponseModelDeserializer
import com.example.moneyexchangeapp.network.interceptors.NetworkInterceptor
import com.example.moneyexchangeapp.util.Constants
import com.example.moneyexchangeapp.workers.SeedDatabaseWorker
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesGsonBuilder(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                LatestExchangeRateResponseModel::class.java,
                ExchangeRateResponseModelDeserializer()
            )
            .create()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        @ApplicationContext context: Context,
        gson: Gson
    ): Retrofit {
        val client = OkHttpClient.Builder()
        client.addInterceptor(NetworkInterceptor(context))

        if (BuildConfig.enabledLogging) {

            val logging = HttpLoggingInterceptor()

            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }

        client.callTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }


    @Provides
    @Singleton
    fun getDatabaseInstance(@ApplicationContext context: Context) =
        synchronized(context) {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                Constants.DataBase.DATABASE_NAME
            ).build()
        }


    @Provides
    @Singleton
    fun getConverterSingleDao(appDatabase: AppDatabase) = appDatabase.getCurrencyRateDao()


    @Singleton
    @Provides
    fun getExchangeRatesService(retrofit: Retrofit): ExchangeRatesService {
        return retrofit.create(ExchangeRatesService::class.java)
    }

}