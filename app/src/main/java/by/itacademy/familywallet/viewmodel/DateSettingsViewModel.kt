package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.viewModelScope
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import by.itacademy.familywallet.utils.UserUtils
import by.itacademy.familywallet.utils.getFirstDayOfMonth
import by.itacademy.familywallet.utils.getLastDayOfMonth
import by.itacademy.familywallet.utils.getYearMonth
import by.itacademy.familywallet.utils.toEndOfDay
import kotlinx.coroutines.launch
import java.util.*

class DateSettingsViewModel : BaseViewModel() {

    override fun getData() {
        isLoading.set(true)
        viewModelScope.launch {
            val partner = repo.getPartner()
            val list = repo.getTransactionsList().transactionsPartnersFilter(partner)
            mutableLiveData.value = map(list).toList()
            isLoading.set(false)
        }
    }

    private fun map(list: List<UIModel.TransactionModel>): Set<UIModel.ArchiveMonthModel> {
        val resultSet = mutableSetOf<UIModel.ArchiveMonthModel>()
        val startDate = UserUtils.getUserCreateDate()
        val endDate = Calendar.getInstance().time.toEndOfDay.time
        list.filter { it.date!! in startDate!!..endDate!! }.forEach { item ->
            resultSet.add(
                UIModel.ArchiveMonthModel(
                    monthAndYear = Date(item.date!!).getYearMonth,
                    startDate = Date(item.date!!).getFirstDayOfMonth.time,
                    endDate = Date(item.date!!).getLastDayOfMonth.time
                )
            )
        }
        return resultSet.sortedBy { it.startDate }.toSet()
    }
}