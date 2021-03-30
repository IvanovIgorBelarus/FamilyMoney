package by.itacademy.familywallet.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.StatisticRecyclerItemBinding
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
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
            else -> CategoryViewHolder(TypeRecyclerItemBinding.inflate(inflater, parent, false), null, null)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (holder) {
            is CategoryViewHolder -> holder.bind(item as UIModel.CategoryModel)
            is OperationsViewHolder -> holder.bind(item as UIModel.TransactionModel)
            is StatisticViewHolder -> holder.bind(item as UIModel.StatisticModel)
        }

    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is UIModel.CategoryModel -> R.string.income
            is UIModel.TransactionModel -> R.string.operations
            is UIModel.StatisticModel -> R.string.statistics
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