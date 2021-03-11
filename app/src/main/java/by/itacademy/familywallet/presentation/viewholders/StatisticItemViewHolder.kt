package by.itacademy.familywallet.presentation.viewholders

import android.icu.text.SimpleDateFormat
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.App
import by.itacademy.familywallet.databinding.StatisticRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel

class StatisticItemViewHolder(private val binding: StatisticRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.TransactionModel?) {
        val preparation = App().viewPreparation
        preparation.prepareView(itemView, item?.type!!)
        with(binding) {
            with(value) {
                text = String.format("%s %s", item?.value.toString(), item?.currency)
                preparation.prepareView(this, item?.type!!)
            }
            with(transactionCategory) {
                text = item?.category
                preparation.prepareView(this, item?.type!!)
            }
            with(moneyType) {
                text = item?.moneyType
                preparation.prepareView(this, item?.type!!)
            }
            with(date) {
                text = String.format("дата операции: %s", SimpleDateFormat("dd.MM.yyyy").format(item?.date))
                preparation.prepareView(this, item?.type!!)
            }
        }
    }
}