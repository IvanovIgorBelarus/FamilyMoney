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

class PreparationTransaction(
    private val binding: ActivityTransactionBinding,
    private val transactionType: String?
) {
    fun setItemsStyles() {
        when (transactionType) {
            EXPENSES -> setRedStyle()
            INCOMES -> setGreenStyle()
        }
    }

    fun createSpinner() {
        with(binding.currencySpinner) {
            when (transactionType) {
                INCOMES -> {
                    adapter = ArrayAdapter(
                        context, R.layout.green_spinner_item,
                        arrayOf("BYN", "USD", "EUR")
                    )
                }
                EXPENSES -> {
                    adapter = ArrayAdapter(
                        context, R.layout.red_spinner_item,
                        arrayOf("BYN", "USD", "EUR")
                    )
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate() =
        SimpleDateFormat("DD/MM/yyyy").format(Timestamp(System.currentTimeMillis()))

    private fun setGreenStyle() {
        with(binding) {
            with(date) {
                addTextChangedListener(DateMask())
                setText(getCurrentDate())
                setTextColor(ContextCompat.getColor(context, R.color.green))
                setBackgroundResource(R.drawable.green_rectangle_button_background)
            }
            with(transactionValue) {
                setTextColor(ContextCompat.getColor(context, R.color.green))
                setBackgroundResource(R.drawable.green_rectangle_button_background)
            }
            with(cashButton) {
                setTextColor(ContextCompat.getColor(context, R.color.green))
                setBackgroundResource(R.drawable.green_rectangle_button_background)
                compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green))
            }
            with(cardButton) {
                setTextColor(ContextCompat.getColor(context, R.color.green))
                setBackgroundResource(R.drawable.green_rectangle_button_background)
                compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green))
            }
        }
    }

    private fun setRedStyle() {
        with(binding) {
            with(date) {
                addTextChangedListener(DateMask())
                setText(getCurrentDate())
                setTextColor(ContextCompat.getColor(context, R.color.red))
                setBackgroundResource(R.drawable.red_rectangle_button_background)
            }
            with(transactionValue) {
                setTextColor(ContextCompat.getColor(context, R.color.red))
                setBackgroundResource(R.drawable.red_rectangle_button_background)
            }
            with(cashButton) {
                setTextColor(ContextCompat.getColor(context, R.color.red))
                setBackgroundResource(R.drawable.red_rectangle_button_background)
                compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            }
            with(cardButton) {
                setTextColor(ContextCompat.getColor(context, R.color.red))
                setBackgroundResource(R.drawable.red_rectangle_button_background)
                compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            }
        }
    }
}