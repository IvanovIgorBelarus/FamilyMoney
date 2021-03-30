package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.ScreenManager
import by.itacademy.familywallet.databinding.ActivityFragmentsBinding
import by.itacademy.familywallet.presentation.MyPagerAdapter
import by.itacademy.familywallet.utils.ProgressBarUtils
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class FragmentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFragmentsBinding
    private val screenManager: ScreenManager by inject { parametersOf(R.id.fragment_container, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewPager()
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
                startActivity(UsersSettingsActivity.start(this))
            }
            R.id.filter -> {
                screenManager.startFragment(DateSettingFragment.newInstance())
//                supportFragmentManager
//                    .beginTransaction()
//                    .add(R.id.fragment_container, DateSettingFragment.newInstance())
//                    .addToBackStack(null)
//                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setViewPager() {
        val viewPager2 = binding.viewPager2
        viewPager2.adapter = MyPagerAdapter(this)
        val tabs = binding.tabLayout
        TabLayoutMediator(tabs, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.setText(R.string.main)
                1 -> tab.setText(R.string.expenses)
                2 -> tab.setText(R.string.income)
                3 -> tab.setText(R.string.operations)
                4 -> tab.setText(R.string.statistics)
            }
        }.attach()
    }

    fun onDateSettingsChange() {
        onBackPressed()
        setViewPager()
    }
}