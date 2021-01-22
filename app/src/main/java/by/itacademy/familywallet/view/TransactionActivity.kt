package by.itacademy.familywallet.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.utils.DateMask
import java.sql.Timestamp
import java.text.SimpleDateFormat

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding.date) {
            addTextChangedListener(DateMask())
            setText(getCurrentDate())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate() =
        SimpleDateFormat("DD/MM/yyyy").format(Timestamp(System.currentTimeMillis()))

}