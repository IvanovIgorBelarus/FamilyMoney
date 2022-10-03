package by.itacademy.familywallet.features.statistics.presentation.view_holder

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.features.start.App
import by.itacademy.familywallet.R
import by.itacademy.familywallet.core.others.EXPENSES
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.core.others.ItemClickListener
import by.itacademy.familywallet.utils.Icons

class StatisticViewHolder(
    private val binding: TypeRecyclerItemBinding,
    private val itemClickListener: ItemClickListener?
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.StatisticModel?) {
        App().viewPreparation.prepareView(itemView, item?.type!!)
        itemView.setOnClickListener { itemClickListener?.onClick(item) }
        val icon = item.icon ?: Icons.getIcons()[0].name
        with(binding.textView) {
            setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(Icons.getIcon(icon).imageRes, context.theme), null, null, null)
            compoundDrawableTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, if (item!!.type == EXPENSES) R.color.expensesColor else R.color.incomesColor))
            text = String.format("%s : %.2f BYN", item?.category, item?.value)
            App().viewPreparation.prepareView(this, item?.type!!)
        }
    }
}