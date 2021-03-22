package by.itacademy.familywallet.utils

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.data.BYN
import by.itacademy.familywallet.data.EUR
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.data.USD
import by.itacademy.familywallet.databinding.ActivityTransactionBinding

class ViewPreparation {
    fun prepareView(view: View, type: String) {
        when (view) {
            is Button -> setItemsStyles(view, type)
            is TextView -> setItemsStyles(view, type)
            is EditText -> setItemsStyles(view, type)
            is Spinner -> setItemsStyles(view, type)
            is View -> setItemsStyles(view, type)
        }
    }

    private fun setItemsStyles(view: View, type: String) {
        when (type) {
            EXPENSES -> setColors(view, R.color.red, R.drawable.red_rectangle_button_background, R.layout.red_spinner_item)
            INCOMES -> setColors(view, R.color.green, R.drawable.green_rectangle_button_background, R.layout.green_spinner_item)
            BANK -> setColors(view, R.color.blue, R.drawable.blue_rectangle_button_background, R.layout.blue_spinner_item)
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
            is EditText -> {
                with(view) {
                    setTextColor(ContextCompat.getColor(context, color))
                    setBackgroundResource(drawableBackground)
                }
            }
            is TextView -> {
                with(view) {
                    setTextColor(ContextCompat.getColor(context, color))
                }
            }
            is Spinner -> {
                with(view) {
                    val currencyArray = arrayOf(BYN, USD, EUR)
                    adapter = ArrayAdapter(context, spinnerItem, currencyArray)
                }
            }
            is View -> view.setBackgroundResource(drawableBackground)
        }
    }

    fun prepareBankViews(binding: ActivityTransactionBinding, context: Context) {
        val color = R.color.blue
        val drawableBackground = R.drawable.blue_rectangle_button_background
        val spinnerItem = R.layout.blue_spinner_item

        with(binding) {
            with(transactionCategoryTitle) {
                setTextColor(ContextCompat.getColor(context, color))
                text = context.getString(R.string.bank)
            }
            with(transactionValue) {
                setTextColor(ContextCompat.getColor(context, color))
                setBackgroundResource(drawableBackground)
            }
            with(currencySpinner) {
                val currencyArray = arrayOf(BYN, USD, EUR)
                adapter = ArrayAdapter(context, spinnerItem, currencyArray)
            }
            with(cashButton) {
                setText(R.string.add)
                setTextColor(ContextCompat.getColor(context, R.color.green))
                setBackgroundResource(R.drawable.green_rectangle_button_background)
                setCompoundDrawables(resources.getDrawable(R.drawable.ic_baseline_arrow_upward_24, context.theme), null, null, null)
                compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green))
            }
            with(cardButton) {
                setText(R.string.out)
                setTextColor(ContextCompat.getColor(context, R.color.red))
                setBackgroundResource(R.drawable.red_rectangle_button_background)
                setCompoundDrawables(resources.getDrawable(R.drawable.ic_baseline_arrow_downward_24, context.theme), null, null, null)
                compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            }
        }
    }
}