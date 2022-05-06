package by.itacademy.familywallet.features.sms.presentation.view_model

import androidx.lifecycle.viewModelScope
import by.itacademy.familywallet.core.others.BaseViewModel
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import kotlinx.coroutines.launch

class SmsViewModel : BaseViewModel() {
    override fun getData(forceLoad: Boolean) {
        isLoading.set(true)
        viewModelScope.launch {
            val list = repo.getSmsList(forceLoad).sortedByDescending { it.date }
            mutableLiveData.value = list
            isLoading.set(false)
        }
    }
}
