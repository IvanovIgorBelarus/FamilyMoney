package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.App
import by.itacademy.familywallet.App.Companion.dateFilterType
import by.itacademy.familywallet.common.currentDateFilter
import by.itacademy.familywallet.common.currentMonthFilter
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.data.DAY_FILTER
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.NO_DATE_FILTER
import by.itacademy.familywallet.data.WEEK_FILTER
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OperationsViewModel(private val repo: DataRepository) : ViewModel() {
    private val mutableLiveData = MutableLiveData<List<UIModel.TransactionModel>>()
    val liveData = mutableLiveData
    fun getAllTransactions() {
        isLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val partner = repo.getPartner()
            val list = repo.getTransactionsList().transactionsPartnersFilter(partner).currentDateFilter().sortedByDescending { it.date }
            withContext(Dispatchers.Main) {
                mutableLiveData.value = list
                isLoading.set(false)
            }
        }
    }
}