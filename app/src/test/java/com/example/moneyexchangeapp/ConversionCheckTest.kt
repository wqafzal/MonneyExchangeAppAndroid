package com.example.moneyexchangeapp

import com.example.moneyexchangeapp.data.model.LatestExchangeRateResponseModel
import com.example.moneyexchangeapp.network.deserializer.ExchangeRateResponseModelDeserializer
import com.example.moneyexchangeapp.util.ConversionUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ConversionCheckTest {

    private lateinit var gson: Gson

    @Before
    fun setGsonConversionFactory() {
        gson = GsonBuilder()
            .registerTypeAdapter(
                LatestExchangeRateResponseModel::class.java,
                ExchangeRateResponseModelDeserializer()
            )
            .create()
    }

    @Test
    fun check_pkr_is_same_after_conversion() {
        val convertFrom = "EUR"
        val convertTo = "PKR"
        val amount = 2.0
        val response = gson.fromJson(jsonResponse, LatestExchangeRateResponseModel::class.java)
        val list = response.rates
        val convertedAmount =
            ConversionUtils.updateConversionFromLatestRates(list, convertFrom, amount)
        assert(convertedAmount.firstOrNull { it.symbol == convertTo }?.convertedAmount == 460.020614)
    }

    @Test
    fun check_conversion_pkr_to_dollar() {
        val convertFrom = "PKR"
        val convertTo = "USD"
        val amount = 1.0
        val response = gson.fromJson(jsonResponse, LatestExchangeRateResponseModel::class.java)
        val list = response.rates
        val convertedAmount =
            ConversionUtils.updateConversionFromLatestRates(list, convertFrom, amount)
        assert(convertedAmount.firstOrNull { it.symbol == convertTo }?.convertedAmount == 0.004493094302943563)
    }

    private var jsonResponse = """
        {
          "base": "EUR",
          "date": "2022-11-17",
          "rates": {
            "AED": 3.795935,
            "AFN": 91.60865,
            "ALL": 116.953442,
            "AMD": 408.763889,
            "ANG": 1.86203,
            "AOA": 523.144093,
            "ARS": 168.242969,
            "AUD": 1.55343,
            "AWG": 1.860225,
            "AZN": 1.778788,
            "BAM": 1.953086,
            "BBD": 2.086139,
            "BDT": 106.41846,
            "BGN": 1.954598,
            "BHD": 0.389512,
            "BIF": 2138.10538,
            "BMD": 1.033458,
            "BND": 1.421562,
            "BOB": 7.139264,
            "BRL": 5.635035,
            "BSD": 1.033234,
            "BTC": 6.2241263e-05,
            "BTN": 84.303324,
            "BWP": 13.435731,
            "BYN": 2.608948,
            "BYR": 20255.780907,
            "BZD": 2.082644,
            "CAD": 1.379439,
            "CDF": 2117.556243,
            "CHF": 0.986032,
            "CLF": 0.034366,
            "CLP": 948.352804,
            "CNY": 7.397186,
            "COP": 5160.749257,
            "CRC": 631.20413,
            "CUC": 1.033458,
            "CUP": 27.386643,
            "CVE": 110.110318,
            "CZK": 24.402018,
            "DJF": 183.931615,
            "DKK": 7.437364,
            "DOP": 56.278124,
            "DZD": 143.655562,
            "EGP": 25.336879,
            "ERN": 15.501873,
            "ETB": 55.184011,
            "EUR": 1,
            "FJD": 2.310502,
            "FKP": 0.86935,
            "GBP": 0.876135,
            "GEL": 2.81614,
            "GGP": 0.86935,
            "GHS": 14.981237,
            "GIP": 0.86935,
            "GMD": 63.661603,
            "GNF": 8900.496682,
            "GTQ": 8.064554,
            "GYD": 216.163199,
            "HKD": 8.089854,
            "HNL": 25.53376,
            "HRK": 7.539101,
            "HTG": 141.547102,
            "HUF": 411.316007,
            "IDR": 16267.769022,
            "ILS": 3.593386,
            "IMP": 0.86935,
            "INR": 84.431727,
            "IQD": 1507.917921,
            "IRR": 43818.628313,
            "ISK": 148.900628,
            "JEP": 0.86935,
            "JMD": 159.087897,
            "JOD": 0.733867,
            "JPY": 145.201393,
            "KES": 126.081782,
            "KGS": 87.275441,
            "KHR": 4277.367036,
            "KMF": 491.412286,
            "KPW": 930.084089,
            "KRW": 1391.923084,
            "KWD": 0.3182,
            "KYD": 0.860966,
            "KZT": 473.474868,
            "LAK": 17865.68387,
            "LBP": 1562.359861,
            "LKR": 379.691492,
            "LRD": 158.893964,
            "LSL": 17.857899,
            "LTL": 3.051534,
            "LVL": 0.625129,
            "LYD": 5.052955,
            "MAD": 11.073783,
            "MDL": 19.785423,
            "MGA": 4440.058212,
            "MKD": 61.573187,
            "MMK": 2169.664797,
            "MNT": 3522.125775,
            "MOP": 8.328858,
            "MRO": 368.944403,
            "MUR": 45.163032,
            "MVR": 15.930782,
            "MWK": 1060.521323,
            "MXN": 20.084329,
            "MYR": 4.705852,
            "MZN": 65.966121,
            "NAD": 17.858379,
            "NGN": 457.150067,
            "NIO": 37.189038,
            "NOK": 10.497331,
            "NPR": 134.885599,
            "NZD": 1.700096,
            "OMR": 0.397881,
            "PAB": 1.033134,
            "PEN": 3.961078,
            "PGK": 3.64048,
            "PHP": 59.384061,
            "PKR": 230.010307,
            "PLN": 4.705077,
            "PYG": 7373.627564,
            "QAR": 3.762813,
            "RON": 4.927737,
            "RSD": 117.313018,
            "RUB": 62.676762,
            "RWF": 1087.186952,
            "SAR": 3.884807,
            "SBD": 8.495457,
            "SCR": 13.670872,
            "SDG": 587.520688,
            "SEK": 11.014298,
            "SGD": 1.423615,
            "SHP": 1.423483,
            "SLE": 18.633286,
            "SLL": 18635.857306,
            "SOS": 588.556128,
            "SRD": 31.485322,
            "STD": 21390.498393,
            "SVC": 9.040344,
            "SYP": 2596.59783,
            "SZL": 18.042093,
            "THB": 37.17325,
            "TJS": 10.41467,
            "TMT": 3.617104,
            "TND": 3.298806,
            "TOP": 2.447694,
            "TRY": 19.23617,
            "TTD": 7.012847,
            "TWD": 32.232735,
            "TZS": 2408.661432,
            "UAH": 38.143778,
            "UGX": 3864.152986,
            "USD": 1.033458,
            "UYU": 41.043152,
            "UZS": 11580.33208,
            "VEF": 972579.361593,
            "VND": 25645.26547,
            "VUV": 122.967069,
            "WST": 2.818166,
            "XAF": 655.017824,
            "XAG": 0.049593,
            "XAU": 0.000587,
            "XCD": 2.792973,
            "XDR": 0.785574,
            "XOF": 655.030482,
            "XPF": 119.470512,
            "YER": 258.623314,
            "ZAR": 18.02651,
            "ZMK": 9302.361559,
            "ZMW": 17.176721,
            "ZWL": 332.773122
          },
          "success": true,
          "timestamp": 1668699482
        }
    """.trimIndent()
}