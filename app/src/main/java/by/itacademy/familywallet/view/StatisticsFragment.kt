package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.R

class StatisticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_statistics, container, false)


    companion object {
        @JvmStatic
        fun newInstance() = StatisticsFragment()
    }
}