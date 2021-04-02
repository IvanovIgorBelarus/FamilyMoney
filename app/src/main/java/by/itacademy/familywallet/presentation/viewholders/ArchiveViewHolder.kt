package by.itacademy.familywallet.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.ItemClickListener

class ArchiveViewHolder(
    private val binding: TypeRecyclerItemBinding,
    private val itemClickListener: ItemClickListener?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.MonthModel) {
        itemView.setOnClickListener { itemClickListener?.onClick(item) }
        binding.textView.text = item.monthAndYear
    }
}