package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.viewModelScope
import by.itacademy.familywallet.common.categoryPartnersFilter
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TypeTransactionViewModel(private val fragmentType: String) : BaseViewModel() {
    override fun getData() {
        isLoading.set(true)
        viewModelScope.launch {
            val list = repo.getCategoriesList()
            val partner = repo.getPartner()
            withContext(Dispatchers.Main) {
                mutableLiveData.value = list.categoryPartnersFilter(partner).filter { item -> (item.type == fragmentType) }
                isLoading.set(false)
            }
        }
    }
}