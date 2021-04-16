package by.itacademy.familywallet.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.App
import by.itacademy.familywallet.data.CARD
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.databinding.StatisticRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.ItemOnLongClickListener
import by.itacademy.familywallet.utils.toStringFormat

class SmsViewHolder(
    private val binding: StatisticRecyclerItemBinding,
    private val itemClickListener: ItemClickListener?,
    private var itemOnLongClickListener: ItemOnLongClickListener?
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.SmsModel) {
        itemView.setOnClickListener { itemClickListener?.onClick(item) }
        itemView.setOnLongClickListener {
            itemOnLongClickListener?.onLongClick(item)
            true
        }

        App().viewPreparation.prepareView(itemView, EXPENSES)
        with(binding) {
            value.text = String.format("%s %s", item.value.toString(), item.currency)
            moneyType.text = CARD
            date.text = String.format("дата операции: %s", item.date?.toStringFormat)
        }
    }
}