package by.itacademy.familywallet.utils

import android.content.res.ColorStateList
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES

class ViewPreparation {
    fun prepareView(view: View, type: String) {
        when (view) {
            is Button -> setItemsStyles(view, type)
        }
    }

    private fun setItemsStyles(view: View, type: String) {
        when (type) {
            EXPENSES -> setColors(view, R.color.red, R.drawable.red_rectangle_button_background, R.layout.red_spinner_item)
            INCOMES -> setColors(view, R.color.green, R.drawable.green_rectangle_button_background, R.layout.green_spinner_item)
        }
    }

    private fun setColors(view: View, color: Int, drawableBackground: Int, spinnerItem: Int) {
        with(view as Button) {
            setTextColor(ContextCompat.getColor(context, color))
            setBackgroundResource(drawableBackground)
            compoundDrawableTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, color))
        }
    }
}