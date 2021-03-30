package by.itacademy.familywallet.model

import by.itacademy.familywallet.data.EXPENSES
import kotlin.math.roundToInt

object PieModelMapper {
    fun map(categories: List<UIModel.CategoryModel>, expenses: List<UIModel.TransactionModel>): List<PieModel> {
        val resultList = mutableListOf<PieModel>()
        val sumValue = expenses.sumByDouble { it.value!! }
        categories.forEach { category ->
            if (category.type == EXPENSES) {
                var categoryValue = expenses.filter { item -> item.category == category.category }.sumByDouble { it.value!! }
                resultList.add(PieModel(value = (categoryValue / sumValue * 10000).toInt().toFloat()/100, category = category.category))
            }
        }
        return resultList
    }
}