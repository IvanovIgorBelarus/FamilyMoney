package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.viewModelScope
import by.itacademy.familywallet.common.categoryFilter
import by.itacademy.familywallet.common.currentDateFilter
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.common.typeFilter
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.PiePreparator
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.launch

class CategoryOperationViewModel(private val item: UIModel.CategoryModel) : BaseViewModel() {
    override fun getData() {
        isLoading.set(true)
        viewModelScope.launch {
            val partner = repo.getPartner()
            val list = repo.getTransactionsList().transactionsPartnersFilter(partner)
                .currentDateFilter()
                .typeFilter(item.type!!)
                .sortedByDescending { it.date }
            val result = if (item.category != "остальные") {
                list.categoryFilter(item.category!!).toMutableList()
            } else {
                list.filter { PiePreparator.getOtherCategories().contains(it.category) }.toMutableList()
            }
            mutableLiveData.value = result
            isLoading.set(false)
        }
    }
}