package by.itacademy.familywallet.view.fragment.viewpager

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.App
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.CategoryChangeWrapper
import by.itacademy.familywallet.databinding.FragmentTypeTransactionBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.ItemOnLongClickListener
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.view.fragment.NewCategoryFragment
import by.itacademy.familywallet.view.fragment.TransactionFragment
import by.itacademy.familywallet.viewmodel.TypeTransactionViewModel
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TypeTransactionFragment : BaseFragment<FragmentAdapter, TypeTransactionViewModel>(R.layout.fragment_type_transaction), ItemClickListener {
    private lateinit var fragmentType: String
    private var isChange: Boolean = false

    private lateinit var binding: FragmentTypeTransactionBinding
    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(this as ItemClickListener, this as ItemOnLongClickListener) }
    override val viewModel by viewModel<TypeTransactionViewModel> { parametersOf(fragmentType) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTypeTransactionBinding.bind(view)
        initViews()
    }

    override fun checkDescribeVisibility(isShowing: Boolean) {
        if (isShowing) {
            binding.titleEmptyAdapter.visibility = View.VISIBLE
        } else {
            binding.titleEmptyAdapter.visibility = View.GONE
        }
    }

    private fun initViews() {
        with(binding) {
            adapterRv.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = fragmentAdapter
            }
            with(categoryCreateButton) {
                setOnClickListener {
                    addFragment(NewCategoryFragment.newInstance(fragmentType))
                }
                App().viewPreparation.prepareView(categoryCreateButton, fragmentType)
            }
            titleEmptyAdapter.text = getString(R.string.category_title_empty)
        }
    }

    override fun onClick(item: Any?) {
        if (isChange) {
            EventBus.getDefault().post(CategoryChangeWrapper((item as UIModel.CategoryModel).category!!))
            onBack()
        } else {
            addFragment(TransactionFragment.newInstance((item as UIModel.CategoryModel).type, item.category))
        }
    }

    companion object {
        fun newInstance(type: String, isChange: Boolean = false) = TypeTransactionFragment().apply {
            fragmentType = type
            this.isChange = isChange
        }
    }
}