package com.example.moneyexchangeapp.data.api

import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.feature.exchange.data.api.ExchangeRatesService
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class ExchangeRateAPITest : BaseServiceTest<ExchangeRatesService>() {

    private lateinit var service: ExchangeRatesService
    private lateinit var result: LatestExchangeRateResponseModel


    @Before
    fun initService() {
        service = createService(ExchangeRatesService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchArticlesListTest()   {
        enqueueResponse("/ExchangeRateAPIResponse.json")
        val baseCurrency = "USD"
        runBlocking {
            result = requireNotNull(service.getLatestRates(convertFrom = baseCurrency))
        }
        mockWebServer.takeRequest()

        assertThat(result.base ,`is` (baseCurrency))



    }
}