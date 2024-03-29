package by.itacademy.familywallet.features.user.view_model

import by.itacademy.familywallet.core.others.BaseViewModel
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.UserUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserSettingsViewModel : BaseViewModel() {
    override fun getData(forceLoad: Boolean) {
    }

    fun createPartner(text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val nullPartner = repo.getPartner()
            repo.deleteItem(nullPartner)
            repo.addPartner(
                UIModel.AccountModel(
                    uid = UserUtils.getUsersUid(),
                    partnerUid = text
                )
            )
        }
    }
}