package by.itacademy.familywallet.model

sealed class UIModel {
    data class AccountModel(
        var id:String?=null,
        var uid: String?=null,
        var partnerUid: String?=null
    )

    data class CategoryModel(
        var id:String?=null,
        val uid: String?,
        var category: String?,
        var type: String?
    )

    data class TransactionModel(
        var id:String?=null,
        val uid: String?,
        val type: String?,
        val category: String?,
        val currency: String?,
        val moneyType: String?,
        val date: Long?,
        val value: Double?
    )
}