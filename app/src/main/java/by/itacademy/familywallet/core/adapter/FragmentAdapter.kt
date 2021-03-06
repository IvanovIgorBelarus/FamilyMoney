package by.itacademy.familywallet.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.IconRecyclerItemBinding
import by.itacademy.familywallet.databinding.StatisticRecyclerItemBinding
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.IconModel
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.core.others.ItemClickListener
import by.itacademy.familywallet.core.others.ItemOnLongClickListener
import by.itacademy.familywallet.features.settings.presentation.view_holder.ArchiveViewHolder
import by.itacademy.familywallet.features.transactions_list.presentation.view_holder.CategoryViewHolder
import by.itacademy.familywallet.features.new_category.presentation.view_holder.IconViewHolder
import by.itacademy.familywallet.features.operations.presantation.view_holder.OperationsViewHolder
import by.itacademy.familywallet.features.sms.presentation.view_holder.SmsViewHolder
import by.itacademy.familywallet.features.statistics.presentation.view_holder.StatisticViewHolder

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
            R.string.statistics -> StatisticViewHolder(TypeRecyclerItemBinding.inflate(inflater, parent, false), itemClickListener)
            R.string.date_setting_title -> ArchiveViewHolder(TypeRecyclerItemBinding.inflate(inflater, parent, false), itemClickListener)
            R.string.choose_tittle -> IconViewHolder(IconRecyclerItemBinding.inflate(inflater, parent, false))
            R.string.sms -> SmsViewHolder(StatisticRecyclerItemBinding.inflate(inflater, parent, false), itemClickListener, itemOnLongClickListener)
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
            is ArchiveViewHolder -> holder.bind(item as UIModel.ArchiveMonthModel)
            is IconViewHolder -> holder.bind(item as IconModel)
            is SmsViewHolder -> holder.bind(item as UIModel.SmsModel)
        }
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is UIModel.CategoryModel -> R.string.income
            is UIModel.TransactionModel -> R.string.operations
            is UIModel.StatisticModel -> R.string.statistics
            is UIModel.ArchiveMonthModel -> R.string.date_setting_title
            is IconModel -> R.string.choose_tittle
            is UIModel.SmsModel -> R.string.sms
            else -> R.layout.type_recycler_item
        }
    }

    fun update(list: List<Any?>) {
        list.forEach {
            if (!this.list.contains(it)) {
                this.list.add(it)
                if (it is UIModel.TransactionModel) {
                    this.list.sortByDescending { item -> (item as UIModel.TransactionModel).date }
                    notifyDataSetChanged()
                } else {
                    notifyItemInserted(itemCount)
                }
            }
        }
        if (this.list.size > list.size) {
            with(this.list) {
                clear()
                addAll(list)
            }
            notifyDataSetChanged()
        }
    }
}