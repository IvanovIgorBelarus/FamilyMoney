package by.itacademy.familywallet.features.transaction.view_model

import androidx.lifecycle.MutableLiveData
import by.itacademy.familywallet.core.others.BANK
import by.itacademy.familywallet.core.others.BANK_MINUS
import by.itacademy.familywallet.core.others.BYN
import by.itacademy.familywallet.core.api.CurrencyApi
import by.itacademy.familywallet.core.others.INCOMES
import by.itacademy.familywallet.core.others.INCOMES_TITLE
import by.itacademy.familywallet.core.others.RUB
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.UserUtils
import by.itacademy.familywallet.core.others.BaseViewModel
import by.itacademy.familywallet.core.others.round
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(private val currencyApi: CurrencyApi) : BaseViewModel() {
    private val mutableLiveDataCurrency = MutableLiveData<Double>()
    val liveDataCurrency = mutableLiveDataCurrency
    override fun getData(forceLoad: Boolean) {
    }

    fun doTransaction(item: UIModel.TransactionModel, currencyValue: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            val moneyT = item.moneyType
            repo.doBakTransactions(item.apply {
                moneyType = BANK_MINUS
                type = BANK
            })
            repo.doTransaction(item.apply {
                moneyType = moneyT
                type = INCOMES
                value = if (currency == RUB) {
                    (value!! * currencyValue * 100).round
                } else {
                    (value!! * currencyValue).round
                }
                category = INCOMES_TITLE
                currency = BYN
            })
            var count = 0
            repo.getCategoriesList().forEach {
                if (it.category == item.category) {
                    count++
                }
            }
            if (count == 0) {
                repo.addNewCategory(
                    UIModel.CategoryModel(
                        uid = UserUtils.getUsersUid(),
                        category = INCOMES_TITLE,
                        type = INCOMES,
                        icon = null
                    )
                )
            }
        }
    }

    fun getCurrency(currency: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var result = 0.0
            val currencyList = currencyApi.getCurrencyList().execute().body()
            currencyList?.forEach {
                if (it.currency == currency) {
                    result = it.rate
                    return@forEach
                }
            }
            withContext(Dispatchers.Main) { mutableLiveDataCurrency.value = result }
        }
    }
}