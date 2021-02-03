package by.itacademy.familywallet.model

import java.util.Date

class TransactionModel(
    val uid: String?,
    val transactionType: String = "empty",
    val date: Date,
    val value: Double
)
