package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.viewModelScope
import by.itacademy.familywallet.common.categoryPartnersFilter
import by.itacademy.familywallet.common.currentDateFilter
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.launch

class StatisticViewModel : BaseViewModel() {

    override fun getData() {
        isLoading.set(true)
        viewModelScope.launch {
            val resultList = mutableSetOf<UIModel.StatisticModel>()
            val incomes = mutableListOf<UIModel.StatisticModel>()
            val expenses = mutableListOf<UIModel.StatisticModel>()
            val partner = repo.getPartner()
            val transactionsList = repo.getTransactionsList().transactionsPartnersFilter(partner).currentDateFilter()
            val categoryList = repo.getCategoriesList().categoryPartnersFilter(partner)
            categoryList.forEach {
                val item = filterForResult(transactionsList, it.category, INCOMES)
                if (item.value != 0.0 && !resultList.contains(item)) {
                    incomes.add(item)
                }
            }
            categoryList.forEach {
                val item = filterForResult(transactionsList, it.category, EXPENSES)
                if (item.value != 0.0 && !resultList.contains(item)) {
                    expenses.add(item)
                }
            }
            with(resultList) {
                addAll(incomes.sortedByDescending { it.value })
                addAll(expenses.sortedByDescending { it.value })
                forEach { item ->
                    categoryList.forEach { category ->
                        if (item.category == category.category) {
                            item.icon = category.icon
                            return@forEach
                        }
                    }
                }
            }
            mutableLiveData.value = resultList.toList()
            isLoading.set(false)
        }
    }

    private fun filterForResult(list: List<UIModel.TransactionModel>, category: String?, type: String): UIModel.StatisticModel {
        val value = list.filter { item -> item.type == type && item.category == category }.sumByDouble { it.value!! }
        return UIModel.StatisticModel(category, type, value)
    }
}