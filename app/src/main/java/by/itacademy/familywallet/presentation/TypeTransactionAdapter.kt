package by.itacademy.familywallet.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding

class TypeTransactionAdapter(private val type: String) :
    RecyclerView.Adapter<TypeTransactionAdapter.TypeTransactionViewHolder>() {
    private var list = mutableListOf<String>("1", "2", "3", "4")
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

    inner class TypeTransactionViewHolder(private val binding: TypeRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            with(binding.textView) {
                text = item
                when (type) {
                    EXPENSES -> {
                        setTextColor(resources.getColor(R.color.red))
                        setBackgroundResource(R.drawable.blue_rectangle_button_background)
                    }
                    INCOMES -> {
                        setTextColor(resources.getColor(R.color.green))
                        setBackgroundResource(R.drawable.green_rectangle_button_background)
                    }
                }
            }
        }
    }
}