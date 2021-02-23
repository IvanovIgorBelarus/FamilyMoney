package by.itacademy.familywallet.di

import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.FirebaseDataBase
import by.itacademy.familywallet.data.FirebaseRepositoryImpl
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.databinding.ActivityTransactionSettingsBinding
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.TypeTransactionAdapter
import by.itacademy.familywallet.utils.PreparationTransactionActivity
import by.itacademy.familywallet.utils.PreparationTransactionSettingsActivity
import by.itacademy.familywallet.viewmodel.StatisticViewModel
import by.itacademy.familywallet.viewmodel.TypeTransactionViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val adapterModule = module {
    factory { (itemClickListener: ItemClickListener, type: String) ->
        TypeTransactionAdapter(itemClickListener, type)
    }
}
val utilsModel = module {
    factory { (binding: ActivityTransactionBinding, transactionType: String?) ->
        PreparationTransactionActivity(binding, transactionType)
    }
    factory { (binding: ActivityTransactionSettingsBinding, transactionType: String?, item: Char?) ->
        PreparationTransactionSettingsActivity(binding, transactionType, item)
    }
}
val dataModule = module {
    single<DataRepository> { FirebaseRepositoryImpl(FirebaseDataBase.instance) }
}
val viewModelModel = module {
    viewModel { TypeTransactionViewModel(repo = get()) }
    viewModel { StatisticViewModel(repo = get()) }
}







