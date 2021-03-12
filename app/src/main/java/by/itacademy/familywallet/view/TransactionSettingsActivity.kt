package by.itacademy.familywallet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.App
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.TRANSACTION_TYPE
import by.itacademy.familywallet.databinding.ActivityTransactionSettingsBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.Dialogs.Companion.createNegativeDialog
import by.itacademy.familywallet.utils.UserUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class TransactionSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionSettingsBinding
    private val repo by inject<DataRepository>()
    private var transactionType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(intent) {
            if (this != null) {
                transactionType = getStringExtra(TRANSACTION_TYPE)
            }
        }
        if (transactionType != null) {
            initViews()
        }
    }

    private fun initViews() {
        with(binding) {
            with(saveButton) {
                App().viewPreparation.prepareView(this, transactionType!!)
                setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        val category = binding.itemName.text.toString()
                        if (!category.isNullOrEmpty()) {
                            repo.addNewCategory(
                                UIModel.CategoryModel(
                                    uid = UserUtils.getUsersUid(),
                                    category = binding.itemName.text.toString(),
                                    type = transactionType!!
                                )
                            )
                            finish()
                        } else {
                            withContext(Dispatchers.Main) { createNegativeDialog(this@TransactionSettingsActivity) }
                        }
                    }
                }
            }
            App().viewPreparation.prepareView(itemName, transactionType!!)
        }
    }

    companion object {
        fun start(context: Context?, transactionType: String) =
            Intent(context, TransactionSettingsActivity::class.java).apply {
                putExtra(TRANSACTION_TYPE, transactionType)
            }
    }
}