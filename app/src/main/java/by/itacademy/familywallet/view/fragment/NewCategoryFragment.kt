package by.itacademy.familywallet.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.IconWrapper
import by.itacademy.familywallet.data.CATEGORY
import by.itacademy.familywallet.data.ICON
import by.itacademy.familywallet.data.ID
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.data.TRANSACTION_TYPE
import by.itacademy.familywallet.data.UID
import by.itacademy.familywallet.databinding.FragmentNewCategoryBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.utils.Icons
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.viewmodel.NewCategoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class NewCategoryFragment : BaseFragment<FragmentAdapter, NewCategoryViewModel>(R.layout.fragment_new_category) {
    private lateinit var binding: FragmentNewCategoryBinding
    var item: UIModel.CategoryModel? = null

    override val viewModel by viewModel<NewCategoryViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (arguments != null) {
            item = UIModel.CategoryModel(
                id = arguments?.getString(ID),
                uid = arguments?.getString(UID),
                category = arguments?.getString(CATEGORY),
                type = arguments?.getString(TRANSACTION_TYPE),
                icon = arguments?.getString(ICON) ?: Icons.getIcons()[0].name
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showActionBar(false)
        binding = FragmentNewCategoryBinding.bind(view)
        if (arguments != null) {
            initViews()
        }
    }

    private fun initViews() {
        with(binding) {
            itemName.setText(item?.category ?: "")
            with(saveButton) {
                setOnClickListener {
                    if (item?.category.isNullOrEmpty()) {
                        createNewCategory()
                    } else {
                        updateCategory()
                    }
                }
            }
            with(iconButton) {
                val icon = item?.icon ?: Icons.getIcons()[0].name
                setImageDrawable(resources.getDrawable(Icons.valueOf(icon).imageRes, context.theme))
                setColorFilter(resources.getColor(R.color.primaryTextColor, context.theme))
                setOnClickListener { addFragment(IconChooseFragment.newInstance()) }
            }
            if (item?.id.isNullOrEmpty()) {
                title.text = String.format(getString(R.string.new_category_tittle), if (item?.type!! == INCOMES) getString(R.string.income) else getString(R.string.expenses))
            } else {
                title.text = String.format(getString(R.string.new_category_update_tittle), if (item?.type!! == INCOMES) getString(R.string.income) else getString(R.string.expenses))
            }
        }
    }

    private fun createNewCategory() {
        item?.category = binding.itemName.text.toString()
        if (!item?.category.isNullOrEmpty()) {
            viewModel.createNewCategory(item!!)
            onBack()
        } else {
            dialog.createNegativeDialog(context!!, getString(R.string.alert_negative_message_category_create))
        }
    }

    override fun listenBus(wrapper: Any) {
        super.listenBus(wrapper)
        when (wrapper) {
            is IconWrapper -> with(binding.iconButton) {
                item?.icon = wrapper.icon
                setImageDrawable(resources.getDrawable(Icons.valueOf(wrapper.icon).imageRes, context.theme))
                setColorFilter(resources.getColor(R.color.primaryTextColor, context.theme))
            }
        }
    }

    private fun updateCategory() {
        with(item!!) {
            category = binding.itemName.text.toString()
            type = item?.type!!
        }
        viewModel.updateCategory(item!!)
        onBack()
    }


    companion object {
        fun newInstance(transactionType: String) = NewCategoryFragment().apply {
            arguments = Bundle().apply {
                putString(TRANSACTION_TYPE, transactionType)
            }
        }

        fun newInstance(item: UIModel.CategoryModel) = NewCategoryFragment().apply {
            arguments = Bundle().apply {
                putString(ID, item.id)
                putString(UID, item.uid)
                putString(CATEGORY, item.category)
                putString(TRANSACTION_TYPE, item.type)
                putString(ICON, item.icon)
            }
        }
    }
}