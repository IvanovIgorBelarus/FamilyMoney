package by.itacademy.familywallet.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.presentation.EXPENSES
import by.itacademy.familywallet.presentation.INCOMES
import by.itacademy.familywallet.presentation.TRANSACTION_TYPE
import by.itacademy.familywallet.utils.DateMask
import java.sql.Timestamp
import java.text.SimpleDateFormat

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding
    private var transactionType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        if (intent != null) {
            transactionType = intent.getStringExtra(TRANSACTION_TYPE)
        }
        if (transactionType != null) {
            setItemsStyles()
        }
        with(binding) {
            cashButton.setOnClickListener { createDialog() }
            cardButton.setOnClickListener { createDialog() }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate() =
        SimpleDateFormat("DD/MM/yyyy").format(Timestamp(System.currentTimeMillis()))

    private fun setItemsStyles() {
        when (transactionType) {
            EXPENSES -> setRedStyle()
            INCOMES -> setGreenStyle()
        }

    }

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

    private fun createDialog() {
        val dialog = TransactionDialog(transactionType)
        dialog.show(supportFragmentManager, "dialog")
    }

    companion object {
        fun startTransactionActivity(context: Context?, transactionType: String) =
            Intent(context, TransactionActivity::class.java).apply {
                putExtra(TRANSACTION_TYPE, transactionType)
            }
    }
}