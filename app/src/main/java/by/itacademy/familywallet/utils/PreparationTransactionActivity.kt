package by.itacademy.familywallet.utils

import android.content.res.ColorStateList
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.databinding.ActivityTransactionBinding


class PreparationTransactionActivity(
    private val binding: ActivityTransactionBinding,
    private val type:String?,
    private val category: String?,
) {
    private val currencyArray = arrayOf("BYN", "USD", "EUR")
    fun setItemsStyles() {
        when (type) {
            EXPENSES -> setColors(R.color.red, R.drawable.red_rectangle_button_background, R.layout.red_spinner_item)
            INCOMES -> setColors(R.color.green, R.drawable.green_rectangle_button_background, R.layout.green_spinner_item)
        }
    }

    private fun setColors(color: Int, drawableBackground: Int, spinnerItem: Int) {
        with(binding) {
            with(transactionCategoryTitle){
                text = category
                setTextColor(ContextCompat.getColor(context, color))
            }
            with(transactionValue) {
                setTextColor(ContextCompat.getColor(context, color))
                setBackgroundResource(drawableBackground)
            }
            with(binding.currencySpinner) {
                adapter = ArrayAdapter(context, spinnerItem, currencyArray)
            }
            with(cashButton) {
                setTextColor(ContextCompat.getColor(context, color))
                setBackgroundResource(drawableBackground)
                compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, color))
            }
            with(cardButton) {
                setTextColor(ContextCompat.getColor(context, color))
                setBackgroundResource(drawableBackground)
                compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, color))
            }
        }
    }
}