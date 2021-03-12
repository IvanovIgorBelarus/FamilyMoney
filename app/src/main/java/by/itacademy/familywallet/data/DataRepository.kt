package by.itacademy.familywallet.data

import by.itacademy.familywallet.model.UIModel

interface DataRepository {
    suspend fun addPartner(accountModel: UIModel.AccountModel)
    suspend fun doTransaction(transactionModel: UIModel.TransactionModel)
    suspend fun addNewCategory(categoryItem: UIModel.CategoryModel)
    suspend fun getPartner():UIModel.AccountModel
    suspend fun getTransactionsList(): List<UIModel.TransactionModel>
    suspend fun getCategoriesList(): List<UIModel.CategoryModel>
    suspend fun deleteItem(categoryItem: Any?)
}