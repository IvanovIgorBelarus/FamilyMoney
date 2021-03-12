package by.itacademy.familywallet.model

sealed class UIModel {
    class AccountModel(
        var uid: String?=null,
        var partnerUid: String?=null
    )

    class CategoryModel(
        val uid: String?,
        var category: String?,
        var type: String?
    )

    class TransactionModel(
        val uid: String?,
        val type: String?,
        val category: String?,
        val currency: String?,
        val moneyType: String?,
        val date: Long?,
        val value: Double?
    )
}