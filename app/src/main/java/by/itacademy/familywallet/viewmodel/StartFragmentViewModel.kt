package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.balanceFilter
import by.itacademy.familywallet.common.categoryTypeFilter
import by.itacademy.familywallet.common.typeFilter
import by.itacademy.familywallet.common.currentDateFilter
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.data.BYN
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.EUR
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.data.RUB
import by.itacademy.familywallet.data.USD
import by.itacademy.familywallet.databinding.FragmentStartBinding
import by.itacademy.familywallet.model.PieModel
import by.itacademy.familywallet.model.PieModelMapper
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.PiePreparator
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import by.itacademy.familywallet.view.fragment.viewpager.StartFragment
import com.google.api.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartFragmentViewModel(private val repo: DataRepository) : BaseViewModel() {
    private val mutableLiveDataExpenses = MutableLiveData<Double>()
    val liveDataExpenses = mutableLiveDataExpenses
    private val mutableLiveDataIncomes = MutableLiveData<Double>()
    val liveDataIncomes = mutableLiveDataIncomes
    private val mutableLiveDataBalance = MutableLiveData<Double>()
    val liveDataBalance = mutableLiveDataBalance
    private val mutableLiveDataBank = MutableLiveData<String>()
    val liveDataDataBank = mutableLiveDataBank
    private val mutableLiveDataPie = MutableLiveData<List<PieModel>>()
    val liveDataPie = mutableLiveDataPie

    override fun getData() {
        isLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val partner = repo.getPartner()
            val list = repo.getTransactionsList().transactionsPartnersFilter(partner)
            val expensesList = list.currentDateFilter().typeFilter(EXPENSES)
            val categories = repo.getCategoriesList().categoryTypeFilter(EXPENSES)
            withContext(Dispatchers.Main) {
                mutableLiveDataExpenses.value = expensesList.sumByDouble { it.value!! }
                mutableLiveDataIncomes.value = list.currentDateFilter().typeFilter(INCOMES)?.sumByDouble { it.value!! }
                mutableLiveDataBalance.value = list.balanceFilter()
                mutableLiveDataBank.value = getBankString(list.typeFilter(BANK))
                mutableLiveDataPie.value = PieModelMapper.map(categories, expensesList)
                isLoading.set(false)
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
