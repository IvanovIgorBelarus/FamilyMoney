package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.common.balanceFilter
import by.itacademy.familywallet.common.categoryFilter
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.data.BYN
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.EUR
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.data.RUB
import by.itacademy.familywallet.data.USD
import by.itacademy.familywallet.model.UIModel
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
    private val mutableLiveDataBank = MutableLiveData<String>()
    val liveDataDataBank = mutableLiveDataBank

    fun getTransactions() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = repo.getTransactionsList()
            val partner = repo.getPartner()
            withContext(Dispatchers.Main) {
                mutableLiveDataExpenses.value = list.transactionsPartnersFilter(partner).categoryFilter(EXPENSES)?.sumByDouble { it.value!! }
                mutableLiveDataIncomes.value = list.transactionsPartnersFilter(partner).categoryFilter(INCOMES)?.sumByDouble { it.value!! }
                mutableLiveDataBalance.value = list.transactionsPartnersFilter(partner).balanceFilter()
                mutableLiveDataBank.value = getBankString(list.transactionsPartnersFilter(partner).categoryFilter(BANK))
            }
        }
    }

    private fun getBankString(list: List<UIModel.TransactionModel>): String {
        var byn = 0.0
        var usd = 0.0
        var eur = 0.0
        var rub = 0.0
        list.forEach { item ->
            when (item.currency) {
                USD -> usd += item.value!!
                EUR -> eur += item.value!!
                RUB -> rub += item.value!!
                BYN -> byn += item.value!!
            }
        }
        return String.format("USD: %.2f\nEUR: %.2f\nRUB: %.2f\nBYN: %.2f", usd, eur, rub, byn)
    }
}
