package by.itacademy.familywallet.presentation.viewholders

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.App
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.ItemClickListener

class CategoryViewHolder(private val binding: TypeRecyclerItemBinding, private val itemClickListener: ItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.CategoryModel?) {
        with(binding.textView) {
            rootView.setOnClickListener { itemClickListener.onClick(item) }
            text = "${item?.category}"
            App().viewPreparation.prepareView(this,item?.type!!)
//            when (item?.type) {
//                EXPENSES -> {
//                    setTextColor(ContextCompat.getColor(context, R.color.red))
//                    setBackgroundResource(R.drawable.red_rectangle_button_background)
//                }
//                INCOMES -> {
//                    setTextColor(ContextCompat.getColor(context, R.color.green))
//                    setBackgroundResource(R.drawable.green_rectangle_button_background)
//                }
//            }
        }
    }
}