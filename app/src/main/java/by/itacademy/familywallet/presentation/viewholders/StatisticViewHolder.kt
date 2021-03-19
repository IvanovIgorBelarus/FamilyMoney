package by.itacademy.familywallet.presentation.viewholders

import android.widget.TextView
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
            gravity=TextView.TEXT_ALIGNMENT_TEXT_START
            text = String.format("%s : %.2f BYN", item?.category, item?.value)
            App().viewPreparation.prepareView(this, item?.type!!)
        }
    }
}