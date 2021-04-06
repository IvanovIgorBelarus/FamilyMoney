package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.common.categoryPartnersFilter
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TypeTransactionViewModel(private val repo: DataRepository, private val fragmentType: String) : BaseViewModel() {
    override fun getTransactions() {
        isLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val list = repo.getCategoriesList()
            val partner = repo.getPartner()
            withContext(Dispatchers.Main) {
                mutableLiveData.value = list.categoryPartnersFilter(partner).filter { item -> (item.type == fragmentType) }
                isLoading.set(false)
            }
        }
    }
}