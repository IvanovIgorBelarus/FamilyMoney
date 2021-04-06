package by.itacademy.familywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected val mutableLiveData = MutableLiveData<List<Any>>()
    val liveData = mutableLiveData
    abstract fun getData()
}