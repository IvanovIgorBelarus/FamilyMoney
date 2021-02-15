package by.itacademy.familywallet.di

import by.itacademy.familywallet.data.FirebaseRepository
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.databinding.ActivityTransactionSettingsBinding
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.TypeTransactionAdapter
import by.itacademy.familywallet.utils.DateMask
import by.itacademy.familywallet.utils.PreparationTransactionActivity
import by.itacademy.familywallet.utils.PreparationTransactionSettingsActivity
import by.itacademy.familywallet.utils.Transaction
import org.koin.dsl.module

const val EXPENSES = "Expenses"
const val INCOMES = "Incomes"
const val TRANSACTION_TYPE = "type"
const val ITEM = "item"
const val TAG = "myname"
val adapterModule = module {
    factory { (itemClickListener: ItemClickListener, type: String) ->
        TypeTransactionAdapter(itemClickListener, type)
    }
}
val utilsModel = module {
    factory { Transaction(get()) }
    factory { (binding: ActivityTransactionBinding, transactionType: String?) ->
        PreparationTransactionActivity(binding, transactionType)
    }
    factory { (binding: ActivityTransactionSettingsBinding, transactionType: String?, item: Char?) ->
        PreparationTransactionSettingsActivity(binding, transactionType, item)
    }
}
val fireBaseModel = module {
    single { FirebaseRepository() }
}






