package by.itacademy.familywallet.di

import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.common.ScreenManager
import by.itacademy.familywallet.common.ScreenManagerImpl
import by.itacademy.familywallet.data.BASE_CURRENCY_URL
import by.itacademy.familywallet.data.CurrencyApi
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
val retrofitModule = module {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_CURRENCY_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCurrencyApi(): CurrencyApi = retrofit.create(CurrencyApi::class.java)
    single { getCurrencyApi() }
}
val viewModelModule = module {
    viewModel { (fragmentType: String) -> TypeTransactionViewModel(fragmentType) }
    viewModel { OperationsViewModel() }
    viewModel { StartFragmentViewModel(get()) }
    viewModel { StatisticViewModel() }
    viewModel { DateSettingsViewModel() }
    viewModel { (category: String) -> CategoryOperationViewModel(category) }
    viewModel { IconChooseViewModel() }
    viewModel { SmsViewModel() }
    viewModel { NewCategoryViewModel() }
    viewModel { UserSettingsViewModel() }
}







