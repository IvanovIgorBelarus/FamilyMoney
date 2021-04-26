package by.itacademy.familywallet.di

import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.common.ScreenManager
import by.itacademy.familywallet.common.ScreenManagerImpl
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.FirebaseDataBase
import by.itacademy.familywallet.data.FirebaseRepositoryImpl
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.ItemOnLongClickListener
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.ViewPreparation
import by.itacademy.familywallet.viewmodel.CategoryOperationViewModel
import by.itacademy.familywallet.viewmodel.DateSettingsViewModel
import by.itacademy.familywallet.viewmodel.IconChooseViewModel
import by.itacademy.familywallet.viewmodel.NewCategoryViewModel
import by.itacademy.familywallet.viewmodel.OperationsViewModel
import by.itacademy.familywallet.viewmodel.SmsViewModel
import by.itacademy.familywallet.viewmodel.StartFragmentViewModel
import by.itacademy.familywallet.viewmodel.StatisticViewModel
import by.itacademy.familywallet.viewmodel.TypeTransactionViewModel
import by.itacademy.familywallet.viewmodel.UserSettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val adapterModule = module {
    factory { (itemClickListener: ItemClickListener?, itemOnLongClickListener: ItemOnLongClickListener) ->
        FragmentAdapter(itemClickListener, itemOnLongClickListener)
    }
}
val utilsModule = module {
    single { Dialogs(get()) }
    single { ViewPreparation() }
}
val commonModule = module {
    single<ScreenManager> { (container: Int, activity: AppCompatActivity) -> ScreenManagerImpl(container, activity) }
}
val dataModule = module {
    single<DataRepository> { FirebaseRepositoryImpl(FirebaseDataBase.instance) }
}
val viewModelModule = module {
    viewModel { (fragmentType: String) -> TypeTransactionViewModel(fragmentType) }
    viewModel { OperationsViewModel() }
    viewModel { StartFragmentViewModel() }
    viewModel { StatisticViewModel() }
    viewModel { DateSettingsViewModel() }
    viewModel { (category: String) -> CategoryOperationViewModel(category) }
    viewModel { IconChooseViewModel() }
    viewModel { SmsViewModel() }
    viewModel { NewCategoryViewModel() }
    viewModel { UserSettingsViewModel() }
}







