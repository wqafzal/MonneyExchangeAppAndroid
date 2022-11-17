package com.example.moneyexchangeapp.repository

import com.example.moneyexchangeapp.base.BaseRepository
import com.example.moneyexchangeapp.data.local.room.dao.CurrencyRateDao
import com.example.moneyexchangeapp.data.remote.fixerApi.FixerService
import javax.inject.Inject

class AppRepository @Inject constructor(
    val fixerService: FixerService,
    val currencyRateDao: CurrencyRateDao,

) : BaseRepository(){

//    fun launchApi(
//        dispatcher: CoroutineDispatcher = Dispatchers.IO,
//        block: suspend CoroutineScope.() -> Unit
//    ) = viewModelScope.launch(dispatcher, block = block)


}