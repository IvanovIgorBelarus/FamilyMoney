package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartFragmentViewModel(
    private val repo: DataRepository
) : ViewModel() {
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
                mutableLiveDataExpenses.value = list
                    .filter { item -> (item.type == EXPENSES) && ((item.uid == partner.uid) || (item.uid == partner.partnerUid)) }
                    ?.sumByDouble { it.value!! }
                mutableLiveDataIncomes.value = list
                    .filter { item -> (item.type == INCOMES) && ((item.uid == partner.uid) || (item.uid == partner.partnerUid)) }
                    ?.sumByDouble { it.value!! }
                mutableLiveDataBalance.value = list.sumByDouble {
                    when (it.type) {
                        EXPENSES -> if ((it.type == EXPENSES) && ((it.uid == partner.uid) || (it.uid == partner.partnerUid))) {
                            -it.value!!
                        } else {
                            0.0
                        }
                        INCOMES -> if ((it.type == INCOMES) && ((it.uid == partner.uid) || (it.uid == partner.partnerUid))) {
                            it.value!!
                        } else {
                            0.0
                        }
                        else -> 0.0
                    }
                }
            }
        }
    }
}
