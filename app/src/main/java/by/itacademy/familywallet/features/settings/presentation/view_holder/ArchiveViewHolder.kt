package by.itacademy.familywallet.features.settings.presentation.view_holder

import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.core.others.BANK
import by.itacademy.familywallet.core.others.ItemClickListener
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.features.start.App
import by.itacademy.familywallet.model.UIModel

class ArchiveViewHolder(
    private val binding: TypeRecyclerItemBinding,
    private val itemClickListener: ItemClickListener?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.ArchiveMonthModel) {
        App().viewPreparation.prepareView(itemView, BANK)
        itemView.setOnClickListener { itemClickListener?.onClick(item) }
        binding.textView.text = item.monthAndYear
    }
}