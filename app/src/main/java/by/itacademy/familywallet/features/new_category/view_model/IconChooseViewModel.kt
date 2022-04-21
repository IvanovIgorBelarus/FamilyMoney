package by.itacademy.familywallet.features.new_category.view_model

import by.itacademy.familywallet.core.others.BaseViewModel
import by.itacademy.familywallet.utils.Icons
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading

class IconChooseViewModel : BaseViewModel() {

    override fun getData(forceLoad: Boolean) {
        isLoading.set(true)
        mutableLiveData.value = Icons.getIcons()
        isLoading.set(false)
    }
}