package by.itacademy.familywallet.di

import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.TypeTransactionAdapter
import org.koin.dsl.module
const val TAG="myname"
val adapterModule = module {
    factory { (itemClickListener: ItemClickListener,type:String)->TypeTransactionAdapter(itemClickListener,type ) }
}