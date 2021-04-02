package by.itacademy.familywallet.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.TAG
import by.itacademy.familywallet.databinding.FragmentViewPagerBinding
import by.itacademy.familywallet.presentation.MyPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_view_pager, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewPagerBinding.bind(view)
        (activity as FragmentsActivity).supportActionBar?.show()
        setViewPager()
    }

    private fun setViewPager() {
        val viewPager = binding.viewPager2
        viewPager.adapter = MyPagerAdapter(this)
        val tabs = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.setText(R.string.main)
                1 -> tab.setText(R.string.expenses)
                2 -> tab.setText(R.string.income)
                3 -> tab.setText(R.string.operations)
                4 -> tab.setText(R.string.statistics)
            }
        }.attach()
    }

    companion object {
        fun newInstance() = ViewPagerFragment()
    }
}