package by.itacademy.familywallet.view.activity

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.App.Companion.dateFilterType
import by.itacademy.familywallet.App.Companion.endDate
import by.itacademy.familywallet.App.Companion.startDate
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.ScreenManager
import by.itacademy.familywallet.data.DAY_FILTER
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.data.MONTH_FILTER
import by.itacademy.familywallet.databinding.ActivityFragmentsBinding
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.ProgressBarUtils
import by.itacademy.familywallet.view.fragment.DateSettingFragment
import by.itacademy.familywallet.view.fragment.UsersSettingsFragment
import by.itacademy.familywallet.view.fragment.viewpager.ViewPagerFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class FragmentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFragmentsBinding
    private val dialog by inject<Dialogs>()
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
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStop() {
        super.onStop()
        getSharedPreferences(DAY_FILTER, Context.MODE_PRIVATE).edit().putString(DAY_FILTER, dateFilterType).apply() // сохраняем фильтр при закрытии приложения
        getSharedPreferences(INCOMES, Context.MODE_PRIVATE).edit().putLong(INCOMES, startDate ?: 0).apply()
        getSharedPreferences(EXPENSES, Context.MODE_PRIVATE).edit().putLong(EXPENSES, endDate ?: 0).apply()
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
        }
        return super.onOptionsItemSelected(item)
    }
}