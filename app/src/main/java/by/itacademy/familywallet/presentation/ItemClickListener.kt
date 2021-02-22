package by.itacademy.familywallet.presentation

import by.itacademy.familywallet.model.CategoryModel

interface ItemClickListener {
    fun onClick(transactionType: String)
    fun onLongClick(transactionType: String, item: CategoryModel)
}