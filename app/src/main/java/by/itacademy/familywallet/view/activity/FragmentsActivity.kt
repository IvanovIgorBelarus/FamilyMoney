package by.itacademy.familywallet.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import by.itacademy.familywallet.App.Companion.dateFilterType
import by.itacademy.familywallet.App.Companion.endDate
import by.itacademy.familywallet.App.Companion.startDate
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.ScreenManager
import by.itacademy.familywallet.common.SmsService
import by.itacademy.familywallet.data.DAY_FILTER
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.data.MONTH_FILTER
import by.itacademy.familywallet.data.NEW_SMS
import by.itacademy.familywallet.databinding.ActivityFragmentsBinding
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.ProgressBarUtils
import by.itacademy.familywallet.view.fragment.DateSettingFragment
import by.itacademy.familywallet.view.fragment.SmsFragment
import by.itacademy.familywallet.view.fragment.UsersSettingsFragment
import by.itacademy.familywallet.view.fragment.viewpager.ViewPagerFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class FragmentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFragmentsBinding
    private val dialog by inject<Dialogs>()
    var opMenu: Menu? = null
    val screenManager: ScreenManager by inject { parametersOf(R.id.fragment_container, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dateFilterType = getSharedPreferences(DAY_FILTER, Context.MODE_PRIVATE).getString(DAY_FILTER, MONTH_FILTER)!!
        startDate = getSharedPreferences(INCOMES, Context.MODE_PRIVATE).getLong(INCOMES, Calendar.getInstance().timeInMillis)!!
        endDate = getSharedPreferences(EXPENSES, Context.MODE_PRIVATE).getLong(EXPENSES, Calendar.getInstance().timeInMillis)!!
        screenManager.startFragment(ViewPagerFragment.newInstance())
        binding.progress = ProgressBarUtils
        binding.executePendingBindings()
        checkPermission()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        opMenu = menu
        val flag = getSharedPreferences(NEW_SMS, Context.MODE_PRIVATE).getString(NEW_SMS, "")
        if (!flag.isNullOrEmpty()) {
            menu?.getItem(1)?.setIcon(R.drawable.ic_baseline_sms_failed)
            getSharedPreferences(NEW_SMS, Context.MODE_PRIVATE).edit().putString(NEW_SMS, "").apply()
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStop() {
        super.onStop()
        getSharedPreferences(DAY_FILTER, Context.MODE_PRIVATE).edit().putString(DAY_FILTER, dateFilterType).apply() // сохраняем фильтр при закрытии приложения
        getSharedPreferences(INCOMES, Context.MODE_PRIVATE).edit().putLong(INCOMES, startDate ?: 0).apply()
        getSharedPreferences(EXPENSES, Context.MODE_PRIVATE).edit().putLong(EXPENSES, endDate ?: 0).apply()
        startService(Intent(this, SmsService::class.java))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.usersSettings -> {
                screenManager.startFragment(UsersSettingsFragment.newInstance())
            }
            R.id.filter -> {
                screenManager.startFragment(DateSettingFragment.newInstance())
            }
            R.id.theme -> {
                val isChecked = getSharedPreferences("1", Context.MODE_PRIVATE).getBoolean("1", true)
                val editor = getSharedPreferences("1", Context.MODE_PRIVATE).edit()
                if (!isChecked) {
                    editor.putBoolean("1", true).apply()
                } else {
                    editor.putBoolean("1", false).apply()
                }
                dialog.createNegativeDialog(this, getString(R.string.change_theme_message))
            }
            R.id.sms -> {
                item.setIcon(R.drawable.ic_baseline_sms_24)
                screenManager.startFragment(SmsFragment.newInstance())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), 120)
    }
}
