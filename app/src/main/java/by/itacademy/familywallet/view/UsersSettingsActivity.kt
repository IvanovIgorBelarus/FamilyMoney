package by.itacademy.familywallet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.App
import by.itacademy.familywallet.App.Companion.dateFilterType
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.DAY_FILTER
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.data.MONTH_FILTER
import by.itacademy.familywallet.data.NO_DATE_FILTER
import by.itacademy.familywallet.data.WEEK_FILTER
import by.itacademy.familywallet.databinding.ActivityUsersSettingsBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.UserUtils
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class UsersSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersSettingsBinding
    private val repo by inject<DataRepository>()
    private val dialog by inject<Dialogs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            saveButton.setOnClickListener {
                createPartner()
            }
            uid.text = UserUtils.getUsersUid()
            title.text = String.format(getString(R.string.uid), UserUtils.getUserName())
            Glide.with(userPhoto)
                .load(UserUtils.getUserPhoto())
                .circleCrop()
                .into(userPhoto)
        }
        enabledButton()
        initButtons()
    }

    private fun initButtons() {
        with(binding) {
            noFilterButton.setOnClickListener {
                dateFilterType = NO_DATE_FILTER
                enabledButton()
                finish()
            }
            monthFilterButton.setOnClickListener {
                dateFilterType = MONTH_FILTER
                enabledButton()
                finish()
            }
            weekFilterButton.setOnClickListener {
                dateFilterType = WEEK_FILTER
                enabledButton()
                finish()
            }
            dayFilterButton.setOnClickListener {
                dateFilterType = DAY_FILTER
                enabledButton()
                finish()
            }
        }
    }

    private fun enabledButton() {
        with(binding) {
            when (dateFilterType) {
                NO_DATE_FILTER -> {
                    noFilterButton.isEnabled = false
                    App().viewPreparation.prepareView(noFilterButton, INCOMES)
                    monthFilterButton.isEnabled = true
                    weekFilterButton.isEnabled = true
                    dayFilterButton.isEnabled = true
                }
                MONTH_FILTER -> {
                    noFilterButton.isEnabled = true
                    monthFilterButton.isEnabled = false
                    App().viewPreparation.prepareView(monthFilterButton, INCOMES)
                    weekFilterButton.isEnabled = true
                    dayFilterButton.isEnabled = true
                }
                WEEK_FILTER -> {
                    noFilterButton.isEnabled = true
                    monthFilterButton.isEnabled = true
                    weekFilterButton.isEnabled = false
                    App().viewPreparation.prepareView(weekFilterButton, INCOMES)
                    dayFilterButton.isEnabled = true
                }
                DAY_FILTER -> {
                    noFilterButton.isEnabled = true
                    monthFilterButton.isEnabled = true
                    weekFilterButton.isEnabled = true
                    dayFilterButton.isEnabled = false
                    App().viewPreparation.prepareView(dayFilterButton, INCOMES)
                }
            }
        }
    }


    private fun createPartner() {
        val text = binding.itemName.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            if (!text.isNullOrEmpty() && text != UserUtils.getUsersUid()) {
                val nullPartner = repo.getPartner()
                repo.deleteItem(nullPartner)
                repo.addPartner(
                    UIModel.AccountModel(
                        uid = UserUtils.getUsersUid(),
                        partnerUid = text
                    )
                )
                finish()
            } else {
                withContext(Dispatchers.Main) { dialog.createNegativeDialog(this@UsersSettingsActivity) }
            }
        }
    }

    companion object {
        fun start(context: Context?) = Intent(context, UsersSettingsActivity::class.java)
    }
}