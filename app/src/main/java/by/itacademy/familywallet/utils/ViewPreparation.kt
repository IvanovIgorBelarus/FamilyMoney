package by.itacademy.familywallet.utils

import android.content.res.ColorStateList
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES

class ViewPreparation {
    fun prepareView(view: View, type: String) {
        when (view) {
            is Button -> setItemsStyles(view, type)
            is TextView -> setItemsStyles(view, type)
            is EditText -> setItemsStyles(view, type)
            is Spinner -> setItemsStyles(view, type)
        }
    }

    private fun setItemsStyles(view: View, type: String) {
        when (type) {
            EXPENSES -> setColors(view, R.color.red, R.drawable.red_rectangle_button_background, R.layout.red_spinner_item)
            INCOMES -> setColors(view, R.color.green, R.drawable.green_rectangle_button_background, R.layout.green_spinner_item)
        }
    }

    private fun setColors(view: View, color: Int, drawableBackground: Int, spinnerItem: Int) {
        when (view) {
            is Button -> {
                with(view) {
                    setTextColor(ContextCompat.getColor(context, color))
                    setBackgroundResource(drawableBackground)
                    compoundDrawableTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(context, color))
                }
            }
            is TextView -> {
                with(view) {
                    setTextColor(ContextCompat.getColor(context, color))
                }
            }
            is EditText -> {
                with(view) {
                    setTextColor(ContextCompat.getColor(context, color))
                    setBackgroundResource(drawableBackground)
                }
            }
            is Spinner -> {
                with(view) {
                    val currencyArray = arrayOf("BYN", "USD", "EUR")
                    adapter = ArrayAdapter(context, spinnerItem, currencyArray)
                }
            }
        }
    }
}