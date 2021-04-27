package by.itacademy.familywallet.model

import com.google.gson.annotations.SerializedName

class CurrencyResponseDTO(
    @SerializedName("Cur_Abbreviation") val currency: String,
    @SerializedName("Cur_Scale") val scale: Double,
    @SerializedName("Cur_OfficialRate") val rate: Double)