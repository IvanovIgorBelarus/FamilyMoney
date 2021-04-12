package by.itacademy.familywallet.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.StatisticRecyclerItemBinding
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.viewholders.ArchiveViewHolder
import by.itacademy.familywallet.presentation.viewholders.CategoryViewHolder
import by.itacademy.familywallet.presentation.viewholders.OperationsViewHolder
import by.itacademy.familywallet.presentation.viewholders.StatisticViewHolder

class FragmentAdapter(
    private val itemClickListener: ItemClickListener? = null,
    private var itemOnLongClickListener: ItemOnLongClickListener? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<Any?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.string.income -> CategoryViewHolder(TypeRecyclerItemBinding.inflate(inflater, parent, false), itemClickListener, itemOnLongClickListener)
            R.string.operations -> OperationsViewHolder(StatisticRecyclerItemBinding.inflate(inflater, parent, false), itemOnLongClickListener)
            R.string.statistics -> StatisticViewHolder(TypeRecyclerItemBinding.inflate(inflater, parent, false))
            R.string.date_setting_title -> ArchiveViewHolder(TypeRecyclerItemBinding.inflate(inflater, parent, false), itemClickListener)
            else -> CategoryViewHolder(TypeRecyclerItemBinding.inflate(inflater, parent, false), null, null)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation_fall_down))
        when (holder) {
            is CategoryViewHolder -> holder.bind(item as UIModel.CategoryModel)
            is OperationsViewHolder -> holder.bind(item as UIModel.TransactionModel)
            is StatisticViewHolder -> holder.bind(item as UIModel.StatisticModel)
            is ArchiveViewHolder -> holder.bind(item as UIModel.MonthModel)
        }
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is UIModel.CategoryModel -> R.string.income
            is UIModel.TransactionModel -> R.string.operations
            is UIModel.StatisticModel -> R.string.statistics
            is UIModel.MonthModel -> R.string.date_setting_title
            else -> R.layout.type_recycler_item
        }
    }

    fun update(list: List<Any?>) {
        with(this.list) {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
}