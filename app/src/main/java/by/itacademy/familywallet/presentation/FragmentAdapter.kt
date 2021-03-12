package by.itacademy.familywallet.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.StatisticRecyclerItemBinding
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.viewholders.CategoryViewHolder
import by.itacademy.familywallet.presentation.viewholders.StatisticItemViewHolder

class FragmentAdapter(
    private val itemClickListener: ItemClickListener? = null,
    private var itemOnLongClickListener: ItemOnLongClickListener? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<Any?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.type_recycler_item -> CategoryViewHolder(TypeRecyclerItemBinding.inflate(inflater, parent, false), itemClickListener, itemOnLongClickListener)
            R.layout.statistic_recycler_item -> StatisticItemViewHolder(StatisticRecyclerItemBinding.inflate(inflater, parent, false), itemOnLongClickListener)
            else -> CategoryViewHolder(TypeRecyclerItemBinding.inflate(inflater, parent, false), itemClickListener, null)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (holder) {
            is CategoryViewHolder -> holder.bind(item as UIModel.CategoryModel)
            is StatisticItemViewHolder -> holder.bind(item as UIModel.TransactionModel)
        }

    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is UIModel.CategoryModel -> R.layout.type_recycler_item
            is UIModel.TransactionModel -> R.layout.statistic_recycler_item
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