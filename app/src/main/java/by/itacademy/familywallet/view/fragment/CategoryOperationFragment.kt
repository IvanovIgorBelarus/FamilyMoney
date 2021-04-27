package by.itacademy.familywallet.view.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.viewmodel.CategoryOperationViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CategoryOperationFragment : BaseFragment<FragmentAdapter, CategoryOperationViewModel>(R.layout.fragment_statistics) {
    private lateinit var binding: FragmentStatisticsBinding

    override val viewModel by viewModel<CategoryOperationViewModel>() { parametersOf(category) }
    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(null, null) }
    private lateinit var category: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)
        initView()
        showActionBar(true)
    }

    private fun initView() {
        with(binding) {
            with(titleTextView) {
                visibility = View.VISIBLE
                text = String.format(getString(R.string.category_operation_title), category)
            }
            adapterRv.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = fragmentAdapter
            }
        }
    }

    companion object {
        fun newInstance(category: String) = CategoryOperationFragment().apply {
            this.category = category
        }
    }
}