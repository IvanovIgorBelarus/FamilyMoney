package by.itacademy.familywallet.core.others

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.core.api.DataRepository
import by.itacademy.familywallet.core.firebase.FirebaseDataBase
import by.itacademy.familywallet.core.firebase.FirebaseRepositoryImpl
import by.itacademy.familywallet.core.repository.DataInteractor

abstract class BaseViewModel : ViewModel() {
    protected val mutableLiveData = MutableLiveData<List<Any>>()
    protected val repo: DataRepository = DataInteractor()
    val liveData = mutableLiveData
    init {
        getData()
    }
    abstract fun getData()
}