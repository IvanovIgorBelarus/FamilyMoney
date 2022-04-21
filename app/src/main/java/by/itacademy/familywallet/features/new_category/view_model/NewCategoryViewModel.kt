package by.itacademy.familywallet.features.new_category.view_model

import by.itacademy.familywallet.core.others.BaseViewModel
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.Icons
import by.itacademy.familywallet.utils.ProgressBarUtils
import by.itacademy.familywallet.utils.UserUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewCategoryViewModel : BaseViewModel() {
    override fun getData(forceLoad: Boolean) {
    }

    fun createNewCategory(item: UIModel.CategoryModel) {
        ProgressBarUtils.isLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            repo.addNewCategory(
                item!!.apply {
                    uid = UserUtils.getUsersUid()
                    this.category = item.category
                    icon = item?.icon ?: Icons.getIcons()[0].name
                }
            )
        }
    }

    fun updateCategory(item: UIModel.CategoryModel) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.upDateItem(item)
        }
    }
}