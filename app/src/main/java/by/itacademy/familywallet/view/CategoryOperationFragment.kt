package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.viewmodel.CategoryOperationViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CategoryOperationFragment : BaseFragment<FragmentAdapter, CategoryOperationViewModel>(R.layout.fragment_statistics) {
    private lateinit var binding: FragmentStatisticsBinding
    override val viewModel by viewModel<CategoryOperationViewModel>() { parametersOf(category) }
    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(null, null) }
    private lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_statistics, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)
        with(binding.statisticTitle) {
            visibility = View.VISIBLE
            text = String.format(getString(R.string.category_operation_title), category)
        }
        (activity as FragmentsActivity).supportActionBar?.show()
        binding.adapterRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fragmentAdapter
        }
        viewModel.getData()
        updateAdapter()
    }

    companion object {
        fun newInstance(category: String) = CategoryOperationFragment().apply {
            this.category = category
        }
    }
}