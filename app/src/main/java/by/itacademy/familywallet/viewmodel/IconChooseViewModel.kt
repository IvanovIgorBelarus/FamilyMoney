package by.itacademy.familywallet.viewmodel

import by.itacademy.familywallet.utils.IconsList
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading

class IconChooseViewModel : BaseViewModel() {

    override fun getData() {
        isLoading.set(true)
        mutableLiveData.value = IconsList.getIcons()
        isLoading.set(false)
    }
}