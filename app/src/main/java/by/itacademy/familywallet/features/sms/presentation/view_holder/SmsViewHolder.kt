package by.itacademy.familywallet.features.sms.presentation.view_holder

import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.features.start.App
import by.itacademy.familywallet.core.others.BANK
import by.itacademy.familywallet.core.others.CARD
import by.itacademy.familywallet.databinding.StatisticRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.core.others.ItemClickListener
import by.itacademy.familywallet.core.others.ItemOnLongClickListener
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

        App().viewPreparation.prepareView(itemView, BANK)
        with(binding) {
            value.text = String.format("%s %s", item.value.toString(), item.currency)
            moneyType.text = CARD
            date.text = String.format("дата операции: %s", item.date?.toStringFormat)
        }
    }
}