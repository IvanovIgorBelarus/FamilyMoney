package by.itacademy.familywallet.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.TypeRecyclerItemBinding


class TypeTransactionAdapter(
    private val itemClickListener: ItemClickListener,
    private val type: String
) :
    RecyclerView.Adapter<TypeTransactionAdapter.TypeTransactionViewHolder>() {
    private var list = mutableListOf<Char>().apply {
        for (i in 'a'..'z') {
            add(i)
        }
    }

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
        holder.itemView.setOnClickListener { itemClickListener.onClick(type) }
    }

    //
    override fun getItemCount() = list.size

    inner class TypeTransactionViewHolder(private val binding: TypeRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Char) {
            with(binding.textView) {
                text = "$item"
                when (type) {
                    EXPENSES -> {
                        setTextColor(ContextCompat.getColor(context,R.color.red))
                        setBackgroundResource(R.drawable.red_rectangle_button_background)
                    }
                    INCOMES -> {
                        setTextColor(ContextCompat.getColor(context,R.color.green))
                        setBackgroundResource(R.drawable.green_rectangle_button_background)
                    }
                }
            }
        }
    }
}