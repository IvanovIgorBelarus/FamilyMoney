package by.itacademy.familywallet.viewmodel

import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SmsViewModel(private val repo: DataRepository) : BaseViewModel() {
    override fun getData() {
        isLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val list = repo.getSmsList().sortedByDescending { it.date }
            withContext(Dispatchers.Main) {
                mutableLiveData.value = list
                isLoading.set(false)
            }
        }
    }
}
