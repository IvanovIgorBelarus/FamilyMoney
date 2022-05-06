package by.itacademy.familywallet.core.others

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.core.api.DataRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {
    protected val mutableLiveData = MutableLiveData<List<Any>>()
    val repo: DataRepository by inject()
    val liveData = mutableLiveData

    init {
        getData()
    }

    abstract fun getData(forceLoad: Boolean = false)
}