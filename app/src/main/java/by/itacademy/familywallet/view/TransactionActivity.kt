package by.itacademy.familywallet.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.databinding.ActivityTransactionBinding
import by.itacademy.familywallet.utils.DateMask

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.date.addTextChangedListener(DateMask())
    }
}