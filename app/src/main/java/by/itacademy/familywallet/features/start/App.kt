package by.itacademy.familywallet.features.start

import android.app.Application
import by.itacademy.familywallet.core.di.adapterModule
import by.itacademy.familywallet.core.di.commonModule
import by.itacademy.familywallet.core.di.dataModule
import by.itacademy.familywallet.core.di.retrofitModule
import by.itacademy.familywallet.core.di.utilsModule
import by.itacademy.familywallet.core.di.viewModelModule
import by.itacademy.familywallet.utils.ViewPreparation
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    val viewPreparation: ViewPreparation by inject()
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(adapterModule, utilsModule, dataModule, viewModelModule, commonModule, retrofitModule)
        }
    }

    companion object {
        lateinit var dateFilterType: String
        var startDate: Long? = null
        var endDate: Long? = null
    }
}