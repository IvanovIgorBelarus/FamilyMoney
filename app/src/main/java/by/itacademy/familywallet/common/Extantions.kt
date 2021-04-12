package by.itacademy.familywallet.common

import by.itacademy.familywallet.App.Companion.dateFilterType
import by.itacademy.familywallet.App.Companion.endDate
import by.itacademy.familywallet.App.Companion.startDate
import by.itacademy.familywallet.data.DAY_FILTER
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.data.MONTH_FILTER
import by.itacademy.familywallet.data.RANGE_FILTER
import by.itacademy.familywallet.data.WEEK_FILTER
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.toEndOfDay
import by.itacademy.familywallet.utils.toStartOfDay
import java.util.*

fun List<UIModel.TransactionModel>.typeFilter(type: String): List<UIModel.TransactionModel> =
    this.filter { item -> item.type == type }

fun List<UIModel.TransactionModel>.balanceFilter(): Double = this.sumByDouble {
    when (it.type) {
        EXPENSES -> -it.value!!
        INCOMES -> it.value!!
        else -> 0.0
    }
}

fun List<UIModel.TransactionModel>.transactionsPartnersFilter(partner: UIModel.AccountModel): List<UIModel.TransactionModel> =
    this.filter { (it.uid == partner.uid) || (it.uid == partner.partnerUid) }

fun List<UIModel.TransactionModel>.currentDayFilter(): List<UIModel.TransactionModel> {
    val firstDay = Calendar.getInstance().time.toStartOfDay.time
    val lastDay = Calendar.getInstance().time.toEndOfDay.time
    return this.filter { it.date!! in firstDay..lastDay }
}

fun List<UIModel.TransactionModel>.currentWeekFilter(): List<UIModel.TransactionModel> {
    val firstDay = Calendar.getInstance().apply { add(Calendar.HOUR_OF_DAY, -7) }.time.toStartOfDay.time
    val lastDay = Calendar.getInstance().time.toEndOfDay.time
    return this.filter { it.date!! in firstDay..lastDay }
}

fun List<UIModel.TransactionModel>.currentMonthFilter(): List<UIModel.TransactionModel> {
    val firstDayOfMonth = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH)) }.time.toStartOfDay.time
    val lastDayOfMonth = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)) }.time.toEndOfDay.time
    return this.filter { it.date!! in firstDayOfMonth..lastDayOfMonth }
}

fun List<UIModel.TransactionModel>.currentRangeFilter(): List<UIModel.TransactionModel> = this.filter { it.date!! in startDate!!..endDate!! }


fun List<UIModel.TransactionModel>.currentDateFilter(): List<UIModel.TransactionModel> {
    return when (dateFilterType) {
        MONTH_FILTER -> this.currentMonthFilter()
        WEEK_FILTER -> this.currentWeekFilter()
        DAY_FILTER -> this.currentDayFilter()
        RANGE_FILTER -> this.currentRangeFilter()
        else -> this
    }
}

fun List<UIModel.CategoryModel>.categoryPartnersFilter(partner: UIModel.AccountModel): List<UIModel.CategoryModel> =
    this.filter { (it.uid == partner.uid) || (it.uid == partner.partnerUid) }

fun List<UIModel.CategoryModel>.categoryTypeFilter(type: String): List<UIModel.CategoryModel> = this.filter { it.type==type }

fun List<UIModel.TransactionModel>.userFilter(uid: String): List<UIModel.TransactionModel> = this.filter { it.uid == uid }.sortedByDescending { it.date }

fun List<UIModel.TransactionModel>.categoryFilter(category: String): List<UIModel.TransactionModel> = this.filter { it.category == category }.sortedByDescending { it.date }