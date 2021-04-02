package by.itacademy.familywallet.view

import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.usersSettings -> {
                screenManager.startFragment(UsersSettingsFragment.newInstance())
            }
            R.id.filter -> {
                screenManager.startFragment(DateSettingFragment.newInstance())
            }
            R.id.theme -> {
//                val isChecked=getSharedPreferences("1",Context.MODE_PRIVATE).getBoolean("1",true)
//                val editor=getSharedPreferences("1",Context.MODE_PRIVATE).edit()
//                if (!isChecked) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    editor.putBoolean("1",true).apply()
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    editor.putBoolean("1",false).apply()
//                }
//                Handler().postDelayed({
//                    restartApp()
//                }, 100)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restartApp() {
       TaskStackBuilder.create(this)
           .addNextIntent(this.intent)
           .startActivities()
    }
}