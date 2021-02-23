package by.itacademy.familywallet.utils

import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.databinding.ActivityTransactionSettingsBinding


class PreparationTransactionSettingsActivity(
    private val binding: ActivityTransactionSettingsBinding,
    private val transactionType: String?,
    private val item: Char?
) {
    fun setItemsStyles() {
        when (transactionType) {
            EXPENSES -> setRedStyle()
            INCOMES -> setGreenStyle()
        }
    }

    private fun setGreenStyle() {
        with(binding) {
            with(itemName) {
                setText(item.toString())
                setTextColor(ContextCompat.getColor(context, R.color.green))
                setBackgroundResource(R.drawable.green_rectangle_button_background)
            }
            with(saveButton) {
                setTextColor(ContextCompat.getColor(context, R.color.green))
                setBackgroundResource(R.drawable.green_rectangle_button_background)
            }
        }
    }

    private fun setRedStyle() {
        with(binding) {
            with(itemName) {
                setText(item.toString())
                setTextColor(ContextCompat.getColor(context, R.color.red))
                setBackgroundResource(R.drawable.red_rectangle_button_background)
            }
            with(saveButton) {
                setTextColor(ContextCompat.getColor(context, R.color.red))
                setBackgroundResource(R.drawable.red_rectangle_button_background)
            }
        }
    }
}