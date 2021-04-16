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
        var icon: String? = null
    )

    data class StatisticModel(
        var category: String?,
        var type: String?,
        val value: Double?,
        var icon: String? = null
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

    data class ArchiveMonthModel(
        val monthAndYear: String,
        val startDate: Long,
        val endDate: Long
    )

    data class SmsModel(
        var id: String? = null,
        var date: Long? = null,
        var value: Double?=null,
        var currency: String? = null
    )
}