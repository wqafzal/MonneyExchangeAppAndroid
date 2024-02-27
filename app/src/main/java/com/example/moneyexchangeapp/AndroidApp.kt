package com.example.moneyexchangeapp

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.example.moneyexchangeapp.core.util.CoroutineHelper
import com.example.moneyexchangeapp.workers.SeedDatabaseWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class AndroidApp : Application(), Configuration.Provider {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: AndroidApp
        var TAG_WORK_NAME: String = "exchange_rates"
        fun context(): Context {
            return instance.applicationContext
        }
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        CoroutineHelper.main {
            scheduleWork()
        }
    }

    private fun scheduleWork() {
        val constraints: Constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
            setRequiresBatteryNotLow(true)
        }.build()
        val work = PeriodicWorkRequest.Builder(
            SeedDatabaseWorker::class.java,
            30,
            TimeUnit.MINUTES
        ).setConstraints(constraints).build()
        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(TAG_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, work)
    }
}