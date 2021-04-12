package by.itacademy.familywallet.presentation.viewholders

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.App
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.ItemOnLongClickListener
import by.itacademy.familywallet.utils.Icons

class CategoryViewHolder(
    private val binding: TypeRecyclerItemBinding,
    private val itemClickListener: ItemClickListener?,
    private var itemOnLongClickListener: ItemOnLongClickListener?
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.CategoryModel?) {
        val icon = if(item?.icon==0) Icons.getIcons()[0] else item?.icon!!
        itemView.setOnClickListener { itemClickListener?.onClick(item) }
        itemView.setOnLongClickListener {
            itemOnLongClickListener?.onLongClick(item)
            true
        }
        App().viewPreparation.prepareView(itemView, item?.type!!)
        with(binding.textView) {
            text = "${item?.category}"
            setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(icon, context.theme), null, null, null)
            compoundDrawableTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, if (item!!.type == EXPENSES) R.color.expensesColor else R.color.incomesColor))
            App().viewPreparation.prepareView(this, item?.type!!)
        }
    }
}