package by.itacademy.familywallet.data

import by.itacademy.familywallet.model.UIModel

interface DataRepository {
    suspend fun doTransaction(transactionModel: UIModel.TransactionModel)
    suspend fun addNewCategory(categoryItem: UIModel.CategoryModel)
    suspend fun getTransactionsList(): List<UIModel.TransactionModel>
    suspend fun getCategoriesList(): List<UIModel.CategoryModel>
}