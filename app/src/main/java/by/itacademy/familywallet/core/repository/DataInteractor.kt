package by.itacademy.familywallet.core.repository

import android.util.Log
import by.itacademy.familywallet.core.api.DataRepository
import by.itacademy.familywallet.core.firebase.FirebaseDataBase
import by.itacademy.familywallet.core.firebase.FirebaseRepositoryImpl
import by.itacademy.familywallet.model.UIModel
import javax.inject.Singleton

class DataInteractor(private val dataStoreStore: DataStore) : DataRepository {

    private val firebaseRepository = FirebaseRepositoryImpl(FirebaseDataBase.instance)

    override suspend fun addPartner(accountModel: UIModel.AccountModel) {
        firebaseRepository.addPartner(accountModel)
        update(accountModel)
    }

    override suspend fun doTransaction(transactionModel: UIModel.TransactionModel, isSms: Boolean) {
        firebaseRepository.doTransaction(transactionModel, isSms)
        update(transactionModel)
    }

    override suspend fun doBakTransactions(transactionModel: UIModel.TransactionModel) {
        firebaseRepository.doBakTransactions(transactionModel)
        update(transactionModel)
    }

    override suspend fun addNewCategory(categoryItem: UIModel.CategoryModel) {
        firebaseRepository.addNewCategory(categoryItem)
        update(categoryItem)
    }

    override suspend fun getSmsList(forceLoad: Boolean): List<UIModel.SmsModel> {
        Log.e("REQUEST", "======================== getSmsList ==========================")
        return get(dataStoreStore.smsList, firebaseRepository.getSmsList(), forceLoad)
    }


    override suspend fun getPartner(forceLoad: Boolean): UIModel.AccountModel {
        Log.e("REQUEST", "======================== getPartner ==========================")
        return get(dataStoreStore.partner, firebaseRepository.getPartner(), forceLoad)
    }

    override suspend fun getTransactionsList(forceLoad: Boolean): List<UIModel.TransactionModel> {
        Log.e("REQUEST", "======================== getTransactionsList ==========================")
        return get(dataStoreStore.transactionsList, firebaseRepository.getTransactionsList(), forceLoad)
    }

    override suspend fun getCategoriesList(forceLoad: Boolean): List<UIModel.CategoryModel> {
        Log.e("REQUEST", "======================== getCategoriesList ==========================")
        return get(dataStoreStore.categoriesList, firebaseRepository.getCategoriesList(), forceLoad)
    }

    override suspend fun deleteItem(item: Any?) {
        firebaseRepository.deleteItem(item)
        update(item)
    }

    override suspend fun upDateItem(item: Any?) {
        firebaseRepository.upDateItem(item)
        update(item)
    }

    private fun <T> get(cache: CacheRepository<T>, request: T, forceLoad: Boolean): T {
        return if (cache.isEmpty() || forceLoad) {
            cache.put(request)
            Log.e("REQUEST", "from request")
            cache.get()
        } else {
            Log.e("REQUEST", "from cache")
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
    }
}