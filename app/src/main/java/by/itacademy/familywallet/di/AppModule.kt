package by.itacademy.familywallet.di

import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.TypeTransactionAdapter
import by.itacademy.familywallet.utils.PreparationTransaction
import org.koin.dsl.module
const val EXPENSES = "расходы"
const val INCOMES = "доходы"
const val TRANSACTION_TYPE="type"
const val TAG = "myname"
val adapterModule = module {
    factory { (itemClickListener: ItemClickListener, type: String) ->
        TypeTransactionAdapter(
            itemClickListener,
            type
        )
    }
}
val utilsModel = module {
    single { (binding: ActivityTransactionBinding, transactionType: String?) ->
        PreparationTransaction(
            binding,
            transactionType
        )
    }
}