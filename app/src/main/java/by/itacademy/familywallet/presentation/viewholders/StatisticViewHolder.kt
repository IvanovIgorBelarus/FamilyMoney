package by.itacademy.familywallet.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.App
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel

class StatisticViewHolder(
    private val binding: TypeRecyclerItemBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.StatisticModel?) {
        App().viewPreparation.prepareView(itemView, item?.type!!)
        with(binding.textView) {
            text = String.format("%s : %.2f BYN", item?.category, item?.value)
            App().viewPreparation.prepareView(this, item?.type!!)
        }
    }
}