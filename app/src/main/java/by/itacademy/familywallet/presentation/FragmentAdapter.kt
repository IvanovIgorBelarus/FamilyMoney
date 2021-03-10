package by.itacademy.familywallet.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.viewholders.CategoryViewHolder

class FragmentAdapter(
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<Any?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.type_recycler_item -> CategoryViewHolder(TypeRecyclerItemBinding.inflate(inflater, parent, false), itemClickListener)
            else -> CategoryViewHolder(TypeRecyclerItemBinding.inflate(inflater, parent, false), itemClickListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (holder) {
            is CategoryViewHolder -> holder.bind(item as UIModel.CategoryModel)
        }

    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is UIModel.CategoryModel -> R.layout.type_recycler_item
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