package by.itacademy.familywallet.presentation.viewholders

import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.databinding.StatisticRecyclerItemBinding
import by.itacademy.familywallet.model.UIModel

class StatisticItemViewHolder(private val binding: StatisticRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UIModel.TransactionModel?) {
        when (item?.type) {
            EXPENSES -> {
                itemView.setBackgroundResource(R.drawable.red_rectangle_button_background)
            }
            INCOMES -> {
                itemView.setBackgroundResource(R.drawable.green_rectangle_button_background)
            }
        }
        with(binding) {
            with(value) {
                text = String.format("%s %s",item?.value.toString(),item?.currency)
                setColor(this, item?.type)
            }
            with(transactionCategory) {
                text = item?.category
                setColor(this, item?.type)
            }
            with(moneyType) {
                text = item?.moneyType
                setColor(this, item?.type)
            }
            with(date) {
                text = String.format("дата операции: %s", SimpleDateFormat("dd.MM.yyyy").format(item?.date))
                setColor(this, item?.type)
            }
        }
    }

    private fun setColor(view: TextView, type: String?) {
        with(view) {
            when (type) {
                EXPENSES -> {
                    setTextColor(ContextCompat.getColor(context, R.color.red))
                    rootView.setBackgroundResource(R.drawable.red_rectangle_button_background)
                }
                INCOMES -> {
                    setTextColor(ContextCompat.getColor(context, R.color.green))
                    rootView.setBackgroundResource(R.drawable.green_rectangle_button_background)
                }
            }
        }
    }
}