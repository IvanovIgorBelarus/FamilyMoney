package by.itacademy.familywallet.data

import by.itacademy.familywallet.model.CategoryModel
import by.itacademy.familywallet.model.TransactionModel

interface DataRepository {
    suspend fun doTransaction(transactionModel: TransactionModel)
    suspend fun addNewCategory(category: String, type:String)
    suspend fun getTransactionsList():List<TransactionModel>
    suspend fun getCategoriesList():List<CategoryModel>
}