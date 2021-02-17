package by.itacademy.familywallet.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.databinding.ActivityFragmentsBinding
import by.itacademy.familywallet.presentation.MyPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FragmentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFragmentsBinding
    private val repo by inject<DataRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewPager()
        CoroutineScope(Dispatchers.IO).launch {
            val list = repo.getCategories()
            list.filter { item->item.type?.toInt()==1 }.forEach { item -> Log.d(by.itacademy.familywallet.data.TAG, "${item.category}  ${item.type}") }
        }
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
                3 -> tab.setText(R.string.statistics)
            }
        }.attach()
    }
}