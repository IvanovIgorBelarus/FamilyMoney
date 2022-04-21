package by.itacademy.familywallet.features.operations.presantation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.wrappers.SettingsChangeWrapper
import by.itacademy.familywallet.common.wrappers.TransactionWrapper
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.core.adapter.FragmentAdapter
import by.itacademy.familywallet.core.others.BaseFragment
import by.itacademy.familywallet.features.operations.view_model.CategoryOperationViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CategoryOperationFragment : BaseFragment<FragmentAdapter, CategoryOperationViewModel>(R.layout.fragment_statistics) {
    private lateinit var binding: FragmentStatisticsBinding

    override val viewModel by viewModel<CategoryOperationViewModel>() { parametersOf(item) }
    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(null, null) }
    private var item = UIModel.CategoryModel()

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
                text = String.format(getString(R.string.category_operation_title), item.category)
            }
            adapterRv.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = fragmentAdapter
            }
        }
    }

    override fun listenBus(wrapper: Any) {
        when (wrapper) {
            is TransactionWrapper -> viewModel.getData(true)
            is SettingsChangeWrapper -> viewModel.getData()
        }
    }

    companion object {
        fun newInstance(category: String, type: String) = CategoryOperationFragment().apply {
            item.category = category
            item.type = type
        }
    }
}