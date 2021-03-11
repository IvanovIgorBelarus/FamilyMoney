package by.itacademy.familywallet.di

import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.FirebaseDataBase
import by.itacademy.familywallet.data.FirebaseRepositoryImpl
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.databinding.ActivityTransactionSettingsBinding
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.utils.PreparationTransactionActivity
import by.itacademy.familywallet.utils.PreparationTransactionSettingsActivity
import by.itacademy.familywallet.viewmodel.StartFragmentViewModel
import by.itacademy.familywallet.viewmodel.StatisticViewModel
import by.itacademy.familywallet.viewmodel.TypeTransactionViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val adapterModule = module {
    factory { (itemClickListener: ItemClickListener) ->
        FragmentAdapter(itemClickListener)
    }
}
val utilsModel = module {
    factory { (binding: ActivityTransactionBinding, type: String?, category: String?) ->
        PreparationTransactionActivity(binding, type, category)
    }
    factory { (binding: ActivityTransactionSettingsBinding, transactionType: String?) ->
        PreparationTransactionSettingsActivity(binding, transactionType)
    }
}
val dataModule = module {
    single<DataRepository> { FirebaseRepositoryImpl(FirebaseDataBase.instance) }
}
val viewModelModel = module {
    viewModel { TypeTransactionViewModel(get()) }
    viewModel { StatisticViewModel(get()) }
    viewModel { StartFragmentViewModel(get()) }
}







