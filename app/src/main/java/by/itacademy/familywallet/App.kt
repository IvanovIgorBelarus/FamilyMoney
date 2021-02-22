package by.itacademy.familywallet

import android.app.Application
import by.itacademy.familywallet.di.adapterModule
import by.itacademy.familywallet.di.dataModule
import by.itacademy.familywallet.di.transactionTypeViewModel
import by.itacademy.familywallet.di.utilsModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(adapterModule, utilsModel, dataModule, transactionTypeViewModel)
        }
    }
}