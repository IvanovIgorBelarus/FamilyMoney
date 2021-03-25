package by.itacademy.familywallet.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.model.UIModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OperationsViewModel(private val repo: DataRepository) : ViewModel() {
    private val mutableLiveData = MutableLiveData<List<UIModel.TransactionModel>>()
    val liveData = mutableLiveData
    val isLoading = ObservableField<Boolean>()
    fun getAllTransactions() {
        isLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val partner = repo.getPartner()
            val list = repo.getTransactionsList().transactionsPartnersFilter(partner).sortedByDescending { it.date }
            withContext(Dispatchers.Main) {
                mutableLiveData.value = list
                isLoading.set(false)
            }
        }
    }
}