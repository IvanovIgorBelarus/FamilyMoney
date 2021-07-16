package by.itacademy.familywallet.features.transactions_list.view_model

import androidx.lifecycle.viewModelScope
import by.itacademy.familywallet.core.others.BaseViewModel
import by.itacademy.familywallet.core.others.categoryPartnersFilter
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.launch

class TypeTransactionViewModel(private val fragmentType: String) : BaseViewModel() {
    override fun getData() {
        isLoading.set(true)
        viewModelScope.launch {
            val list = repo.getCategoriesList()
            val partner = repo.getPartner()
            mutableLiveData.value = list.categoryPartnersFilter(partner).filter { item -> (item.type == fragmentType) }
            isLoading.set(false)
        }
    }
}