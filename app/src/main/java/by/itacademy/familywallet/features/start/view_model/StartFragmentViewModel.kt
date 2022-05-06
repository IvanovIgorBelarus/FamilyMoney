package by.itacademy.familywallet.features.start.view_model

import androidx.lifecycle.MutableLiveData
import by.itacademy.familywallet.core.others.BANK
import by.itacademy.familywallet.core.others.BYN
import by.itacademy.familywallet.core.api.CurrencyApi
import by.itacademy.familywallet.core.others.BaseViewModel
import by.itacademy.familywallet.core.others.EUR
import by.itacademy.familywallet.core.others.EXPENSES
import by.itacademy.familywallet.core.others.INCOMES
import by.itacademy.familywallet.core.others.RUB
import by.itacademy.familywallet.core.others.USD
import by.itacademy.familywallet.core.others.balanceFilter
import by.itacademy.familywallet.core.others.categoryTypeFilter
import by.itacademy.familywallet.core.others.currentDateFilter
import by.itacademy.familywallet.core.others.transactionsPartnersFilter
import by.itacademy.familywallet.core.others.typeFilter
import by.itacademy.familywallet.model.CurrencyResponseDTO
import by.itacademy.familywallet.model.PieModel
import by.itacademy.familywallet.model.PieModelMapper
import by.itacademy.familywallet.model.StartModel
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartFragmentViewModel(private val currencyApi: CurrencyApi) : BaseViewModel() {

    private val mutableLiveDataStart = MutableLiveData<StartModel>()
    val liveDataStart = mutableLiveDataStart

    override fun getData(forceLoad: Boolean) {
        isLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val partner = repo.getPartner()
            val list = repo.getTransactionsList(forceLoad).transactionsPartnersFilter(partner)
            val expensesList = list.currentDateFilter().typeFilter(EXPENSES)
            val categories = repo.getCategoriesList(forceLoad).categoryTypeFilter(EXPENSES)

//            val currencyList = currencyApi.getCurrencyList()
            withContext(Dispatchers.Main) {

                mutableLiveDataStart.value = StartModel(
                    expenses = expensesList.sumByDouble { it.value!! },
                    incomes = list.currentDateFilter().typeFilter(INCOMES).sumByDouble { it.value!! },
                    balance = list.balanceFilter(),
                    bankString = getBankString(list.typeFilter(BANK)),
                    pieData = PieModelMapper.map(categories, expensesList),
                    currencyString = getCurrencyString(emptyList())
                )
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

    private fun getCurrencyString(list: List<CurrencyResponseDTO>?): String {
        var usd = 0.0
        var eur = 0.0
        var rub = 0.0
        list?.forEach {
            when (it.currency) {
                USD -> usd = it.rate / it.scale
                EUR -> eur = it.rate / it.scale
                RUB -> rub = it.rate
            }
        }
        return String.format("USD: %.4f\nEUR: %.4f\nRUB: %.4f\n\n", usd, eur, rub)
    }
}
