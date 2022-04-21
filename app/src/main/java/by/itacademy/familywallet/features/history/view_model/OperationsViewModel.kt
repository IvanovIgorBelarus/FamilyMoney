package by.itacademy.familywallet.features.history.view_model

import androidx.lifecycle.viewModelScope
import by.itacademy.familywallet.core.others.BaseViewModel
import by.itacademy.familywallet.core.others.currentDateFilter
import by.itacademy.familywallet.core.others.transactionsPartnersFilter
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.launch

class OperationsViewModel : BaseViewModel() {
    override fun getData(forceLoad: Boolean) {
        isLoading.set(true)
        viewModelScope.launch {
            val partner = repo.getPartner()
            val list = repo.getTransactionsList(forceLoad).transactionsPartnersFilter(partner).currentDateFilter().sortedByDescending { it.date }
            mutableLiveData.value = list
            isLoading.set(false)
        }
    }
}