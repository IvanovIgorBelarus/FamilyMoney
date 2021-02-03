package by.itacademy.familywallet.utils

import by.itacademy.familywallet.data.FirebaseRepository
import by.itacademy.familywallet.model.TransactionModel

class Transaction(private val db: FirebaseRepository) {
    fun doTransaction(transactionType: String?, transactionModel: TransactionModel) {
        db.instance.collection(transactionType!!).add(
            mapOf(
                "date" to transactionModel.date,
                "value" to transactionModel.value,
                "uid" to transactionModel.uid
            )
        )
    }
}
