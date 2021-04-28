package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.viewModelScope
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.launch

class SmsViewModel : BaseViewModel() {
    override fun getData() {
        isLoading.set(true)
        viewModelScope.launch {
            val list = repo.getSmsList().sortedByDescending { it.date }
            mutableLiveData.value = list
            isLoading.set(false)
        }
    }
}
