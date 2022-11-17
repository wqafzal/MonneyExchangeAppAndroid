package com.example.moneyexchangeapp.di

import android.content.Context
import com.example.moneyexchangeapp.BuildConfig
import com.example.moneyexchangeapp.data.HistoricalDataResponseModel
import com.example.moneyexchangeapp.data.model.FixerSymbolsResponseModel
import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.data.remote.fixerApi.FixerService
import com.example.moneyexchangeapp.network.deserializer.CountryDeserializer
import com.example.moneyexchangeapp.network.deserializer.ExchangeRateResponseModelDeserializer
import com.example.moneyexchangeapp.network.deserializer.HistoricalDataResponseSerializer
import com.example.moneyexchangeapp.network.interceptors.NetworkInterceptor
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
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    companion object {
        const val HEADER_API_KEY = "apikey"
    }

    @Provides
    @Singleton
    fun providesGsonBuilder(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(FixerSymbolsResponseModel::class.java, CountryDeserializer())
            .registerTypeAdapter(
                LatestExchangeRateResponseModel::class.java,
                ExchangeRateResponseModelDeserializer()
            )
            .registerTypeAdapter(
                HistoricalDataResponseModel::class.java,
                HistoricalDataResponseSerializer()
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
        client.addInterceptor {
            it.proceed(
                it.request().newBuilder().addHeader(HEADER_API_KEY, BuildConfig.apiKey).build()
            )
        }

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

    @Singleton
    @Provides
    fun getFixerService(retrofit: Retrofit): FixerService {
        return retrofit.create(FixerService::class.java)
    }

}