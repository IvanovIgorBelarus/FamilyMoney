package by.itacademy.familywallet.viewmodel

import by.itacademy.familywallet.common.currentDateFilter
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OperationsViewModel() : BaseViewModel() {
    override fun getData() {
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