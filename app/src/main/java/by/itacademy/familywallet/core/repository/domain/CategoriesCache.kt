package by.itacademy.familywallet.core.repository.domain

import by.itacademy.familywallet.core.repository.CacheRepository
import by.itacademy.familywallet.model.UIModel
import javax.inject.Singleton

@Singleton
class CategoriesCache : CacheRepository<List<UIModel.CategoryModel>> {

    private var categoriesList: List<UIModel.CategoryModel>? = null

    override fun put(cache: List<UIModel.CategoryModel>) {
        categoriesList = cache
    }

    override fun get(): List<UIModel.CategoryModel> = categoriesList.orEmpty()

    override fun clear() {
        categoriesList = null
    }

    override fun isEmpty(): Boolean = categoriesList.isNullOrEmpty()
}