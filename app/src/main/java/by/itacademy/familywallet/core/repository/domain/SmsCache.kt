package by.itacademy.familywallet.core.repository.domain

import by.itacademy.familywallet.core.repository.CacheRepository
import by.itacademy.familywallet.model.UIModel

class SmsCache : CacheRepository<List<UIModel.SmsModel>> {

    private var smsList: List<UIModel.SmsModel>? = null

    override fun put(cache: List<UIModel.SmsModel>) {
        smsList = cache
    }

    override fun get(): List<UIModel.SmsModel> = smsList.orEmpty()

    override fun clear() {
        smsList = null
    }

    override fun isEmpty(): Boolean = smsList.isNullOrEmpty()
}