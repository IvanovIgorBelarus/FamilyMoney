package by.itacademy.familywallet

import android.app.Application
import android.content.Context
import by.itacademy.familywallet.data.DAY_FILTER
import by.itacademy.familywallet.di.adapterModule
import by.itacademy.familywallet.di.commonModule
import by.itacademy.familywallet.di.dataModule
import by.itacademy.familywallet.di.utilsModule
import by.itacademy.familywallet.di.viewModelModule
import by.itacademy.familywallet.utils.ViewPreparation
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.*

class App : Application() {
    val viewPreparation: ViewPreparation by inject()
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(adapterModule, utilsModule, dataModule, viewModelModule, commonModule)
        }
    }

    companion object {
        lateinit var dateFilterType: String
        var startDate: Long? = Calendar.getInstance().timeInMillis
        var endDate: Long? = Calendar.getInstance().timeInMillis
    }
}