package by.itacademy.familywallet

import android.app.Application
import by.itacademy.familywallet.di.adapterModule
import by.itacademy.familywallet.di.dataModule
import by.itacademy.familywallet.di.viewModelModel
import by.itacademy.familywallet.di.utilsModel
import by.itacademy.familywallet.utils.ViewPreparation
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    val viewPreparation:ViewPreparation by inject()
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(adapterModule, utilsModel, dataModule, viewModelModel)
        }
    }
}