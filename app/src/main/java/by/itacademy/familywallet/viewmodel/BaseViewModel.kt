package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.FirebaseDataBase
import by.itacademy.familywallet.data.FirebaseRepositoryImpl

abstract class BaseViewModel() : ViewModel() {
    protected val mutableLiveData = MutableLiveData<List<Any>>()
    protected val repo: DataRepository= FirebaseRepositoryImpl(FirebaseDataBase.instance)
    val liveData = mutableLiveData
    abstract fun getData()
}