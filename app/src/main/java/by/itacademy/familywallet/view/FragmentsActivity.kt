package by.itacademy.familywallet.view

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.ScreenManager
import by.itacademy.familywallet.databinding.ActivityFragmentsBinding
import by.itacademy.familywallet.utils.ProgressBarUtils
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class FragmentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFragmentsBinding
    val screenManager: ScreenManager by inject { parametersOf(R.id.fragment_container, this) }
    private var isChecked=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        screenManager.startFragment(ViewPagerFragment.newInstance())
        binding.progress = ProgressBarUtils
        binding.executePendingBindings()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.usersSettings -> {
                screenManager.startFragment(UsersSettingsFragment.newInstance())
            }
            R.id.filter -> {
                screenManager.startFragment(DateSettingFragment.newInstance())
            }
//            R.id.theme -> {
//                isChecked = !isChecked
//                if (isChecked) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                }
//            }
        }
        return super.onOptionsItemSelected(item)
    }
}