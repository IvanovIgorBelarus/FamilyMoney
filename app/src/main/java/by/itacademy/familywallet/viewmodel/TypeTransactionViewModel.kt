package by.itacademy.familywallet.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.common.categoryPartnersFilter
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.model.UIModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TypeTransactionViewModel(private val repo: DataRepository) : ViewModel() {
    private val mutableLiveData = MutableLiveData<List<UIModel.CategoryModel>>()
    val liveData = mutableLiveData
    val isLoading = ObservableField<Boolean>()
    fun getTransactionTypeList(fragmentType: String) {
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