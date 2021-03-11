package by.itacademy.familywallet.model

sealed class UIModel{
    class CategoryModel (
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