package by.itacademy.familywallet.core.api

import by.itacademy.familywallet.model.CurrencyResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("rates")
    suspend fun getCurrencyList(@Query("periodicity") period: String = "0"): List<CurrencyResponseDTO>
}