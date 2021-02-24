package by.itacademy.familywallet.utils

import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.databinding.ActivityTransactionSettingsBinding


class PreparationTransactionSettingsActivity(
    private val binding: ActivityTransactionSettingsBinding,
    private val transactionType: String?
) {
    fun setItemsStyles() {
        when (transactionType) {
            EXPENSES -> setStyle(R.color.red, R.drawable.red_rectangle_button_background)
            INCOMES -> setStyle(R.color.green, R.drawable.green_rectangle_button_background)
        }
    }

    private fun setStyle(color: Int, drawable: Int) {
        with(binding) {
            with(itemName) {
                setTextColor(ContextCompat.getColor(context, color))
                setBackgroundResource(drawable)
            }
            with(saveButton) {
                setTextColor(ContextCompat.getColor(context, color))
                setBackgroundResource(drawable)
            }
        }
    }
}