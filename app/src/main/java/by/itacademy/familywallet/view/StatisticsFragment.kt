package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemOnLongClickListener
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.viewmodel.StatisticViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StatisticsFragment : Fragment(), ItemOnLongClickListener {
    private val statisticViewModel by viewModel<StatisticViewModel>()
    private lateinit var binding: FragmentStatisticsBinding
    private val fragmentAdapter: FragmentAdapter by inject { parametersOf(null, this as ItemOnLongClickListener) }
    private val dialog by inject<Dialogs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_statistics, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)
        statisticViewModel.liveData.observe(this, Observer { list -> fragmentAdapter.update(list) })
        binding.adapterRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fragmentAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        statisticViewModel.getAllTransactions()
    }

    companion object {
        @JvmStatic
        fun newInstance() = StatisticsFragment()
    }

    override fun onLongClick(item: Any?) {
        dialog.deleteDialog(item, this)
        this.onResume()
    }
}