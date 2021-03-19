package by.itacademy.familywallet.common

import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.model.UIModel

fun List<UIModel.TransactionModel>.categoryFilter(category: String): Double =
    this.filter { item -> item.type == category }?.sumByDouble { it.value!! }

fun List<UIModel.TransactionModel>.balanceFilter(): Double = this.sumByDouble {
    when (it.type) {
        EXPENSES ->  -it.value!!
        INCOMES ->  it.value!!
        else -> 0.0
    }
}

fun List<UIModel.TransactionModel>.transactionsPartnersFilter(partner: UIModel.AccountModel): List<UIModel.TransactionModel> =
    this.filter { (it.uid == partner.uid) || (it.uid == partner.partnerUid) }

fun List<UIModel.CategoryModel>.categoryPartnersFilter(partner: UIModel.AccountModel): List<UIModel.CategoryModel> =
    this.filter { (it.uid == partner.uid) || (it.uid == partner.partnerUid) }

fun List<UIModel.TransactionModel>.userFilter(uid: String): List<UIModel.TransactionModel> = this.filter { it.uid == uid }.sortedByDescending { it.date }

fun List<UIModel.TransactionModel>.typeFilter(type: String): List<UIModel.TransactionModel> = this.filter { it.type == type }.sortedByDescending { it.date }