package by.itacademy.familywallet.features.operations.presantation.view_holder

import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.features.start.App
import by.itacademy.familywallet.databinding.StatisticRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.core.others.ItemOnLongClickListener
import by.itacademy.familywallet.utils.toStringFormat

class OperationsViewHolder(
    private val binding: StatisticRecyclerItemBinding,
    private var itemOnLongClickListener: ItemOnLongClickListener?
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.TransactionModel?) {
        itemView.setOnLongClickListener {
            itemOnLongClickListener?.onLongClick(item)
            true
        }
        val preparation = App().viewPreparation
        preparation.prepareView(itemView, item?.type!!)
        with(binding) {
            with(value) {
                text = String.format("%s %s", item.value.toString(), item.currency)
                preparation.prepareView(this, item.type.toString())
            }
            with(transactionCategory) {
                text = item.category
                preparation.prepareView(this, item.type.toString())
            }
            with(moneyType) {
                text = item.moneyType
                preparation.prepareView(this, item.type.toString())
            }
            with(date) {
                text = String.format("дата операции: %s", item.date?.toStringFormat)
                preparation.prepareView(this, item.type.toString())
            }
        }
    }
}