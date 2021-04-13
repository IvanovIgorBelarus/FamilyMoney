package by.itacademy.familywallet.utils

import by.itacademy.familywallet.model.IconModel

object IconsList {
    fun getIcons(): List<IconModel> {
        val resultList = mutableListOf<IconModel>()
        Icons.values().forEach { resultList.add(IconModel(it.name, it.imageRes)) }
        return resultList
    }
}