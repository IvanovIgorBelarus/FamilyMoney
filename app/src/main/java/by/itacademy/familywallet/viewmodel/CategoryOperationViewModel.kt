package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.common.categoryFilter
import by.itacademy.familywallet.common.currentDateFilter
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.common.typeFilter
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.PiePreparator
import by.itacademy.familywallet.utils.ProgressBarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryOperationViewModel(private val repo: DataRepository) : ViewModel() {
    private val mutableLiveData = MutableLiveData<List<UIModel.TransactionModel>>()
    val liveData = mutableLiveData
    fun getTransactions(category: String) {
        ProgressBarUtils.isLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val partner = repo.getPartner()
            val list = repo.getTransactionsList().transactionsPartnersFilter(partner)
                .currentDateFilter()
                .typeFilter(EXPENSES)
                .sortedByDescending { it.date }
            val result = if (category != "остальные") {
                list.categoryFilter(category).toMutableList()
            } else {
                list.filter { PiePreparator.getOtherCategories().contains(it.category) }.toMutableList()
            }
            withContext(Dispatchers.Main) {
                mutableLiveData.value = result
                ProgressBarUtils.isLoading.set(false)
            }
        }
    }
}