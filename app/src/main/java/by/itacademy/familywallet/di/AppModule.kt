package by.itacademy.familywallet.di

import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.TypeTransactionAdapter
import by.itacademy.familywallet.view.TypeTransactionFragment
import org.koin.dsl.module

val adapterModule = module {
    single<ItemClickListener> { TypeTransactionFragment() }
    factory { TypeTransactionAdapter(get()) }
}