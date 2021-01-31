package by.itacademy.familywallet.model

import java.sql.Timestamp

class TransactionModel(
    val id: Int,
    val uid: String,
    val transactionType: String,
    val date: Timestamp,
    val value: Double
)
