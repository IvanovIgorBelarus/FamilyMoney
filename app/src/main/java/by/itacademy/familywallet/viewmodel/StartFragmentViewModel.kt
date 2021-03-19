package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.common.balanceFilter
import by.itacademy.familywallet.common.categoryFilter
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartFragmentViewModel(private val repo: DataRepository) : ViewModel() {
    private val mutableLiveDataExpenses = MutableLiveData<Double>()
    val liveDataExpenses = mutableLiveDataExpenses
    private val mutableLiveDataIncomes = MutableLiveData<Double>()
    val liveDataIncomes = mutableLiveDataIncomes
    private val mutableLiveDataBalance = MutableLiveData<Double>()
    val liveDataBalance = mutableLiveDataBalance

    fun getTransactions() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = repo.getTransactionsList()
            val partner = repo.getPartner()
            withContext(Dispatchers.Main) {
                mutableLiveDataExpenses.value = list.transactionsPartnersFilter(partner).categoryFilter(EXPENSES)
                mutableLiveDataIncomes.value = list.transactionsPartnersFilter(partner).categoryFilter(INCOMES)
                mutableLiveDataBalance.value = list.transactionsPartnersFilter(partner).balanceFilter()
            }
        }
    }
}
