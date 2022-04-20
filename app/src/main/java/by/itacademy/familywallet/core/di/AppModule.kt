package by.itacademy.familywallet.core.di

import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.core.screen_manager.ScreenManager
import by.itacademy.familywallet.core.screen_manager.ScreenManagerImpl
import by.itacademy.familywallet.core.others.BASE_CURRENCY_URL
import by.itacademy.familywallet.core.api.CurrencyApi
import by.itacademy.familywallet.core.api.DataRepository
import by.itacademy.familywallet.core.firebase.FirebaseDataBase
import by.itacademy.familywallet.core.firebase.FirebaseRepositoryImpl
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.core.adapter.FragmentAdapter
import by.itacademy.familywallet.core.others.ItemClickListener
import by.itacademy.familywallet.core.others.ItemOnLongClickListener
import by.itacademy.familywallet.core.repository.DataInteractor
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.ViewPreparation
import by.itacademy.familywallet.features.operations.view_model.CategoryOperationViewModel
import by.itacademy.familywallet.features.settings.viewmodel.DateSettingsViewModel
import by.itacademy.familywallet.features.new_category.view_model.IconChooseViewModel
import by.itacademy.familywallet.features.new_category.view_model.NewCategoryViewModel
import by.itacademy.familywallet.features.history.view_model.OperationsViewModel
import by.itacademy.familywallet.features.sms.presentation.view_model.SmsViewModel
import by.itacademy.familywallet.features.start.view_model.StartFragmentViewModel
import by.itacademy.familywallet.features.statistics.view_model.StatisticViewModel
import by.itacademy.familywallet.features.transaction.view_model.TransactionViewModel
import by.itacademy.familywallet.features.transactions_list.view_model.TypeTransactionViewModel
import by.itacademy.familywallet.features.user.view_model.UserSettingsViewModel
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
    single<DataRepository> { DataInteractor() }
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
    viewModel { (item: UIModel.CategoryModel) -> CategoryOperationViewModel(item) }
    viewModel { IconChooseViewModel() }
    viewModel { SmsViewModel() }
    viewModel { NewCategoryViewModel() }
    viewModel { UserSettingsViewModel() }
    viewModel { TransactionViewModel(get()) }
}







