package by.itacademy.familywallet.common

import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.model.UIModel

fun List<UIModel.TransactionModel>.categoryFilter(type: String, partner: UIModel.AccountModel): Double =
    this.filter { item -> (item.type == type) && ((item.uid == partner.uid) || (item.uid == partner.partnerUid)) }?.sumByDouble { it.value!! }

fun List<UIModel.TransactionModel>.balanceFilter(partner: UIModel.AccountModel): Double = this.sumByDouble {
    when (it.type) {
        EXPENSES -> if ((it.uid == partner.uid) || (it.uid == partner.partnerUid)) {
            -it.value!!
        } else {
            0.0
        }
        INCOMES -> if ((it.uid == partner.uid) || (it.uid == partner.partnerUid)) {
            it.value!!
        } else {
            0.0
        }
        else -> 0.0
    }
}

fun List<UIModel.TransactionModel>.byDateFilter(partner: UIModel.AccountModel): List<UIModel.TransactionModel> =
    this.filter { (it.uid == partner.uid) || (it.uid == partner.partnerUid) }.sortedByDescending { it.date }

fun List<UIModel.TransactionModel>.userFilter(uid: String): List<UIModel.TransactionModel> = this.filter { it.uid == uid }.sortedByDescending { it.date }

fun List<UIModel.TransactionModel>.typeFilter(type: String): List<UIModel.TransactionModel> = this.filter { it.type == type }.sortedByDescending { it.date }