package by.itacademy.familywallet.presentation.viewholders

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.App
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.presentation.ItemClickListener

class IconViewHolder(
    private val binding: TypeRecyclerItemBinding,
    private val itemClickListener: ItemClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Int) {
        App().viewPreparation.prepareView(itemView, BANK)
        itemView.setOnClickListener { itemClickListener?.onClick(item) }
        with(binding.textView) {
            setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(item, context.theme), null, null, null)
            compoundDrawableTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primaryTextColor))
        }
    }
}