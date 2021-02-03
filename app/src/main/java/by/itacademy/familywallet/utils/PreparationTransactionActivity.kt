package by.itacademy.familywallet.utils

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.di.EXPENSES
import by.itacademy.familywallet.di.INCOMES
import java.sql.Timestamp
import java.text.SimpleDateFormat

class PreparationTransactionActivity(
    private val binding: ActivityTransactionBinding,
    private val transactionType: String?,
    private val dateMask: DateMask
) {
    private val currencyArray = arrayOf("BYN", "USD", "EUR")
    fun setItemsStyles() {
        when (transactionType) {
            EXPENSES -> setColors(R.color.red, R.drawable.red_rectangle_button_background, R.layout.red_spinner_item)
            INCOMES -> setColors(R.color.green, R.drawable.green_rectangle_button_background, R.layout.green_spinner_item)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate() =
        SimpleDateFormat("DD/MM/yyyy").format(Timestamp(System.currentTimeMillis()))

    private fun setColors(color: Int, drawableBackground: Int, spinnerItem: Int) {
        with(binding) {
            with(date) {
                addTextChangedListener(dateMask)
                setText(getCurrentDate())
                setTextColor(ContextCompat.getColor(context, color))
                setBackgroundResource(drawableBackground)
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