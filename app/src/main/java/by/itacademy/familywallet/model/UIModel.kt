package by.itacademy.familywallet.model

sealed class UIModel {
    data class AccountModel(
        var id: String? = null,
        var uid: String? = null,
        var partnerUid: String? = null
    )

    data class CategoryModel(
        var id: String? = null,
        var uid: String? = null,
        var category: String? = null,
        var type: String? = null,
        var icon: Int = 0
    )

    data class StatisticModel(
        var category: String?,
        var type: String?,
        val value: Double?
    )

    data class TransactionModel(
        var id: String? = null,
        var uid: String? = null,
        var type: String? = null,
        var category: String? = null,
        var currency: String? = null,
        var moneyType: String? = null,
        var date: Long? = null,
        var value: Double? = null
    )

    data class MonthModel(
        val monthAndYear: String,
        val startDate: Long,
        val endDate: Long
    )
}