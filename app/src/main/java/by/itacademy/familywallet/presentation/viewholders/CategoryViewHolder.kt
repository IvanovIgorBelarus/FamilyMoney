package by.itacademy.familywallet.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.App
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.ItemClickListener

class CategoryViewHolder(private val binding: TypeRecyclerItemBinding, private val itemClickListener: ItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.CategoryModel?) {
        with(binding.textView) {
            rootView.setOnClickListener { itemClickListener.onClick(item) }
            text = "${item?.category}"
            App().viewPreparation.prepareView(this, item?.type!!)
        }
    }
}