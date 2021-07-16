package by.itacademy.familywallet.core.others

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.core.api.DataRepository
import by.itacademy.familywallet.core.firebase.FirebaseDataBase
import by.itacademy.familywallet.core.firebase.FirebaseRepositoryImpl

abstract class BaseViewModel : ViewModel() {
    protected val mutableLiveData = MutableLiveData<List<Any>>()
    protected val repo: DataRepository = FirebaseRepositoryImpl(FirebaseDataBase.instance)
    val liveData = mutableLiveData
    abstract fun getData()
}