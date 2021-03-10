package by.itacademy.familywallet.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding
import by.itacademy.familywallet.model.CategoryModel

class TypeTransactionAdapter(
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<TypeTransactionAdapter.TypeTransactionViewHolder>() {
    private var list = mutableListOf<CategoryModel?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeTransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TypeTransactionViewHolder(
            TypeRecyclerItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TypeTransactionViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun update(list: List<CategoryModel?>) {
        with(this.list){
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class TypeTransactionViewHolder(private val binding: TypeRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryModel?) {
            with(binding.textView) {
                rootView.setOnClickListener { itemClickListener.onClick(item) }
                text = "${item?.category}"
                when (item?.type) {
                    EXPENSES -> {
                        setTextColor(ContextCompat.getColor(context, R.color.red))
                        setBackgroundResource(R.drawable.red_rectangle_button_background)
                    }
                    INCOMES -> {
                        setTextColor(ContextCompat.getColor(context, R.color.green))
                        setBackgroundResource(R.drawable.green_rectangle_button_background)
                    }
                }
            }
        }
    }
}