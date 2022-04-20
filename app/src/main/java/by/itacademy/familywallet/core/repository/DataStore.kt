package by.itacademy.familywallet.core.repository

import by.itacademy.familywallet.core.repository.domain.CategoriesCache
import by.itacademy.familywallet.core.repository.domain.PartnerCache
import by.itacademy.familywallet.core.repository.domain.SmsCache
import by.itacademy.familywallet.core.repository.domain.TransactionsCache

class DataStore {
    val smsList = SmsCache()
    val partner = PartnerCache()
    val transactionsList = TransactionsCache()
    val categoriesList = CategoriesCache()
}