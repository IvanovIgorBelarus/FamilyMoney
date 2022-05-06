package by.itacademy.familywallet.core.repository.domain

import by.itacademy.familywallet.core.repository.CacheRepository
import by.itacademy.familywallet.model.UIModel
import javax.inject.Singleton

object TransactionsCache: CacheRepository<List<UIModel.TransactionModel>> {

    private var transactionsList: List<UIModel.TransactionModel>? = null

    override fun put(cache: List<UIModel.TransactionModel>) {
       transactionsList = cache
    }

    override fun get(): List<UIModel.TransactionModel> = transactionsList.orEmpty()

    override fun clear() {
        transactionsList = null
    }

    override fun isEmpty(): Boolean = transactionsList.isNullOrEmpty()
}