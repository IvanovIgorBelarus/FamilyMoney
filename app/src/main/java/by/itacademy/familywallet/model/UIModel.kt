package by.itacademy.familywallet.model

import java.util.*

sealed class UIModel {
    data class AccountModel(
        var id: String? = null,
        var uid: String? = null,
        var partnerUid: String? = null
    )

    data class CategoryModel(
        var id: String? = null,
        val uid: String?,
        var category: String?,
        var type: String?
    )

    data class StatisticModel(
        var category: String?,
        var type: String?,
        val value: Double?
    )

    data class TransactionModel(
        var id: String? = null,
        val uid: String?,
        val type: String?,
        val category: String?,
        val currency: String?,
        val moneyType: String?,
        val date: Long?,
        var value: Double?
    )

    data class MonthModel(
        val monthAndYear: String,
        val startDate: Long,
        val endDate: Long
    )
}