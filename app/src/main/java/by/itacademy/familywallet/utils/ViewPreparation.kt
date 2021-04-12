package by.itacademy.familywallet.utils

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES

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
            EXPENSES -> setColors(view, R.color.expensesColor, R.drawable.expenses_rectangle_button_background, R.drawable.ic_baseline_add_circle_outline_expenses)
            INCOMES -> setColors(view, R.color.incomesColor, R.drawable.incomes_rectangle_button_background, R.drawable.ic_baseline_add_circle_outline_incomes)
            BANK ->setColors(view, R.color.primaryTextColor, R.drawable.primary_rectangle_button_background, R.drawable.ic_baseline_add_circle_outline_incomes)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "UseCompatTextViewDrawableApis")
    private fun setColors(view: View, color: Int, drawableBackground: Int, icon: Int) {
        when (view) {
            is Button -> with(view) {
                setTextColor(ContextCompat.getColor(context, color))
                setBackgroundResource(drawableBackground)
                compoundDrawableTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, color))
            }
            is ImageButton -> {
                with(view) {
                    setImageDrawable(resources.getDrawable(icon, context.theme))
                }
            }
            is EditText -> {
                with(view) {
                    setTextColor(ContextCompat.getColor(context, color))
                    setHintTextColor(ContextCompat.getColor(context, color))
                    setBackgroundResource(drawableBackground)
                }
            }
            is TextView -> {
                with(view) {
                    setTextColor(ContextCompat.getColor(context, color))
                }
            }
            is View -> view.setBackgroundResource(drawableBackground)
        }
    }
}