package by.itacademy.familywallet.utils

import android.content.res.ColorStateList
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.di.EXPENSES
import by.itacademy.familywallet.di.INCOMES

class PreparationTransactionActivity(
    private val binding: ActivityTransactionBinding,
    private val transactionType: String?,
) {
    private val currencyArray = arrayOf("BYN", "USD", "EUR")
    fun setItemsStyles() {
        when (transactionType) {
            EXPENSES -> setColors(R.color.red, R.drawable.red_rectangle_button_background, R.layout.red_spinner_item)
            INCOMES -> setColors(R.color.green, R.drawable.green_rectangle_button_background, R.layout.green_spinner_item)
        }
    }

    private fun setColors(color: Int, drawableBackground: Int, spinnerItem: Int) {
        with(binding) {
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