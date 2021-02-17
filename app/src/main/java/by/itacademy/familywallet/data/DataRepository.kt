package by.itacademy.familywallet.data

import by.itacademy.familywallet.model.CategoryModel
import by.itacademy.familywallet.model.TransactionModel

interface DataRepository {
    fun doTransaction(transactionType: String?, transactionModel: TransactionModel)
    suspend fun getCategories():List<CategoryModel>
}