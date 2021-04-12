package by.itacademy.familywallet.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.App
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.ItemClickListener

class ArchiveViewHolder(
    private val binding: TypeRecyclerItemBinding,
    private val itemClickListener: ItemClickListener?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.MonthModel) {
        App().viewPreparation.prepareView(itemView, BANK)
        itemView.setOnClickListener { itemClickListener?.onClick(item) }
        binding.textView.text = item.monthAndYear
    }
}