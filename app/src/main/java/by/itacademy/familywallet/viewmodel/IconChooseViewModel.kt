package by.itacademy.familywallet.viewmodel

import by.itacademy.familywallet.utils.Icons
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading

class IconChooseViewModel : BaseViewModel() {

    override fun getData() {
        isLoading.set(true)
        mutableLiveData.value = Icons.getIcons()
        isLoading.set(false)
    }
}