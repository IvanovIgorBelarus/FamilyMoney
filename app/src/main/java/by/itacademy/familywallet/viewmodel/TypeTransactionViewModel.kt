package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.model.CategoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TypeTransactionViewModel(
    private val repo: DataRepository
) : ViewModel() {
    private val mutableLiveData = MutableLiveData<List<CategoryModel>>()
    val liveData = mutableLiveData
    fun getTransactionTypeList(fragmentType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val list = repo.getCategoriesList()
            withContext(Dispatchers.Main) { mutableLiveData.value = list.filter { item -> item.type == fragmentType } }
        }
    }
}