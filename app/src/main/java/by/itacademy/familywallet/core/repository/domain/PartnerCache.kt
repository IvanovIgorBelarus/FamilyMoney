package by.itacademy.familywallet.core.repository.domain

import by.itacademy.familywallet.core.repository.CacheRepository
import by.itacademy.familywallet.model.UIModel
import javax.inject.Singleton

object PartnerCache: CacheRepository<UIModel.AccountModel> {

    private  var partner: UIModel.AccountModel? = null

    override fun put(cache: UIModel.AccountModel) {
        partner = cache
    }

    override fun get(): UIModel.AccountModel = partner!!

    override fun clear() {
       partner = null
    }

    override fun isEmpty(): Boolean = partner == null
}