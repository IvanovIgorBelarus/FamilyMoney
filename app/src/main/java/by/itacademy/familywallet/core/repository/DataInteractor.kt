package by.itacademy.familywallet.core.repository

import android.util.Log
import by.itacademy.familywallet.common.wrappers.TransactionWrapper
import by.itacademy.familywallet.common.wrappers.UpdateWrapper
import by.itacademy.familywallet.core.api.DataRepository
import by.itacademy.familywallet.core.firebase.FirebaseDataBase
import by.itacademy.familywallet.core.firebase.FirebaseRepositoryImpl
import by.itacademy.familywallet.core.repository.domain.CategoriesCache
import by.itacademy.familywallet.core.repository.domain.PartnerCache
import by.itacademy.familywallet.core.repository.domain.SmsCache
import by.itacademy.familywallet.core.repository.domain.TransactionsCache
import by.itacademy.familywallet.model.UIModel
import org.greenrobot.eventbus.EventBus

object DataInteractor : DataRepository {

    private val firebaseRepository = FirebaseRepositoryImpl(FirebaseDataBase.instance)

    override suspend fun addPartner(accountModel: UIModel.AccountModel) {
        firebaseRepository.addPartner(accountModel)
        update(accountModel)
    }

    override suspend fun doTransaction(transactionModel: UIModel.TransactionModel, isSms: Boolean) {
        firebaseRepository.doTransaction(transactionModel, isSms)
    }

    override suspend fun doBakTransactions(transactionModel: UIModel.TransactionModel) {
        firebaseRepository.doBakTransactions(transactionModel)
    }

    override suspend fun addNewCategory(categoryItem: UIModel.CategoryModel) {
        firebaseRepository.addNewCategory(categoryItem)
    }

    override suspend fun getSmsList(forceLoad: Boolean): List<UIModel.SmsModel> {
        return get(SmsCache, firebaseRepository.getSmsList(), forceLoad)
    }


    override suspend fun getPartner(forceLoad: Boolean): UIModel.AccountModel {
        return get(PartnerCache, firebaseRepository.getPartner(), forceLoad)
    }

    override suspend fun getTransactionsList(forceLoad: Boolean): List<UIModel.TransactionModel> {
        return get(TransactionsCache, firebaseRepository.getTransactionsList(), forceLoad)
    }

    override suspend fun getCategoriesList(forceLoad: Boolean): List<UIModel.CategoryModel> {
        return get(CategoriesCache, firebaseRepository.getCategoriesList(), forceLoad)
    }

    override suspend fun deleteItem(item: Any?) {
        firebaseRepository.deleteItem(item)
    }

    override suspend fun upDateItem(item: Any?) {
        firebaseRepository.upDateItem(item)
        update(item)
    }

    private fun <T> get(cache: CacheRepository<T>, request: T, forceLoad: Boolean): T {
        return if (cache.isEmpty() || forceLoad) {
            Log.d("REQUEST13","FROM SERVER")
            cache.put(request)
            cache.get()
        } else {
            cache.get()
        }
    }


    suspend fun update(item: Any?) {
        when (item) {
            is UIModel.CategoryModel -> getCategoriesList(forceLoad = true)
            is UIModel.AccountModel -> getPartner(forceLoad = true)
            is UIModel.TransactionModel -> getTransactionsList(forceLoad = true)
            is UIModel.SmsModel -> getSmsList(forceLoad = true)
        }
        EventBus.getDefault().post(UpdateWrapper())
    }
}