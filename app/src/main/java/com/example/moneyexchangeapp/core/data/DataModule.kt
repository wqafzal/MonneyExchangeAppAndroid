package com.example.moneyexchangeapp.core.data

import android.content.Context
import androidx.room.Room
import com.example.moneyexchangeapp.BuildConfig
import com.example.moneyexchangeapp.feature.exchange.data.api.ExchangeRatesService
import com.example.moneyexchangeapp.feature.exchange.data.api.ExchangeRateResponseDeserializer
import com.example.moneyexchangeapp.core.data.network.NetworkInterceptor
import com.example.moneyexchangeapp.core.util.Constants
import com.example.moneyexchangeapp.feature.exchange.data.ExchangeRateRepositoryImpl
import com.example.moneyexchangeapp.feature.exchange.data.api.LatestExchangeRateResponse
import com.example.moneyexchangeapp.feature.exchange.domain.ExchangeRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Provides
    @Singleton
    fun providesGsonBuilder(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                LatestExchangeRateResponse::class.java,
                ExchangeRateResponseDeserializer()
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
    fun getExchangeRatesService(retrofit: Retrofit): ExchangeRatesService {
        return retrofit.create(ExchangeRatesService::class.java)
    }

    @Binds
    abstract fun bindExchangeRateRepository(
        exchangeRateRepositoryImpl: ExchangeRateRepositoryImpl
    ): ExchangeRepository
}