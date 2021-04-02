package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.common.transactionsPartnersFilter
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import by.itacademy.familywallet.utils.UserUtils
import by.itacademy.familywallet.utils.getFirstDayOfMonth
import by.itacademy.familywallet.utils.getLastDayOfMonth
import by.itacademy.familywallet.utils.getYearMonth
import by.itacademy.familywallet.utils.toEndOfDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class DateSettingsViewModel(private val repo: DataRepository) : ViewModel() {
    private val mutableLiveData = MutableLiveData<List<UIModel.MonthModel>>()
    val liveData = mutableLiveData
    fun getMonthList() {
        isLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val partner = repo.getPartner()
            val list = repo.getTransactionsList().transactionsPartnersFilter(partner)
            withContext(Dispatchers.Main) {
                mutableLiveData.value = map(list).toList()
                isLoading.set(false)
            }
        }
    }

    private fun map(list: List<UIModel.TransactionModel>): Set<UIModel.MonthModel> {
        val resultSet = mutableSetOf<UIModel.MonthModel>()
        val startDate = UserUtils.getUserCreateDate()
        val endDate = Calendar.getInstance().time.toEndOfDay.time
        list.filter { it.date!! in startDate!!..endDate!! }.forEach { item ->
            resultSet.add(
                UIModel.MonthModel(
                    monthAndYear = Date(item.date!!).getYearMonth,
                    startDate = Date(item.date!!).getFirstDayOfMonth.time,
                    endDate = Date(item.date!!).getLastDayOfMonth.time
                )
            )
        }
        return resultSet.sortedBy { it.startDate }.toSet()
    }
}