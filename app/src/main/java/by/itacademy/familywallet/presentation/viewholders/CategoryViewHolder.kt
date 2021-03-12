package by.itacademy.familywallet.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.App
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.ItemOnLongClickListener

class CategoryViewHolder(
    private val binding: TypeRecyclerItemBinding,
    private val itemClickListener: ItemClickListener?,
    private var itemOnLongClickListener: ItemOnLongClickListener?
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.CategoryModel?) {
        itemView.setOnClickListener { itemClickListener?.onClick(item) }
        itemView.setOnLongClickListener {
            itemOnLongClickListener?.onLongClick(item)
            true
        }
        App().viewPreparation.prepareView(itemView, item?.type!!)
        with(binding.textView) {
            text = "${item?.category}"
            App().viewPreparation.prepareView(this, item?.type!!)
        }
    }
}