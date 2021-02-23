package by.itacademy.familywallet.presentation

import by.itacademy.familywallet.model.CategoryModel

interface ItemClickListener {
    fun onClick(item: CategoryModel)
    fun onLongClick(transactionType: String, item: CategoryModel)
}