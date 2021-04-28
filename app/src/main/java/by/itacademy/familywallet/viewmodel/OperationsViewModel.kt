package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.viewModelScope
import by.itacademy.familywallet.common.currentDateFilter
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.launch

class OperationsViewModel : BaseViewModel() {
    override fun getData() {
        isLoading.set(true)
        viewModelScope.launch {
            val partner = repo.getPartner()
            val list = repo.getTransactionsList().transactionsPartnersFilter(partner).currentDateFilter().sortedByDescending { it.date }
            mutableLiveData.value = list
            isLoading.set(false)
        }
    }
}