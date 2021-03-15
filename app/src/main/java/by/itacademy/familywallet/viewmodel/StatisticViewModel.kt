package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.common.byDateFilter
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.model.UIModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatisticViewModel(
    private val repo: DataRepository
) : ViewModel() {
    private val mutableLiveData = MutableLiveData<List<UIModel.TransactionModel>>()
    val liveData = mutableLiveData
    fun getAllTransactions() {
        CoroutineScope(Dispatchers.IO).launch {
            val partner = repo.getPartner()
            val list = repo.getTransactionsList().byDateFilter(partner)
            withContext(Dispatchers.Main) { mutableLiveData.value = list }
        }
    }
}