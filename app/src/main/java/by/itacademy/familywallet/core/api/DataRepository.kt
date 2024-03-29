package by.itacademy.familywallet.core.api

import by.itacademy.familywallet.model.UIModel

interface DataRepository {
    suspend fun addPartner(accountModel: UIModel.AccountModel)
    suspend fun doTransaction(transactionModel: UIModel.TransactionModel, isSms: Boolean = false)
    suspend fun doBakTransactions(transactionModel: UIModel.TransactionModel)
    suspend fun addNewCategory(categoryItem: UIModel.CategoryModel)
    suspend fun getSmsList(forceLoad: Boolean = false): List<UIModel.SmsModel>
    suspend fun getPartner(forceLoad: Boolean = false): UIModel.AccountModel
    suspend fun getTransactionsList(forceLoad: Boolean = false): List<UIModel.TransactionModel>
    suspend fun getCategoriesList(forceLoad: Boolean = false): List<UIModel.CategoryModel>
    suspend fun deleteItem(item: Any?)
    suspend fun upDateItem(item: Any?)
}