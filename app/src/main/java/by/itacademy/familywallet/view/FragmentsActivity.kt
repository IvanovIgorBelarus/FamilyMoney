package by.itacademy.familywallet.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.ActivityFragmentsBinding
import by.itacademy.familywallet.presentation.MyPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FragmentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFragmentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewPager()
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
                3->tab.setText(R.string.statistics)
            }
        }.attach()
    }
}