package by.itacademy.familywallet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.databinding.ActivityUsersSettingsBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.UserUtils
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
                val text = itemName.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    if (!text.isNullOrEmpty()) {
                        val nullPartner=repo.getPartner()
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
            uid.text = UserUtils.getUsersUid()
        }
    }

    companion object {
        fun start(context: Context?) = Intent(context, UsersSettingsActivity::class.java)
    }
}