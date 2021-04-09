package by.itacademy.familywallet.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.IconWrapper
import by.itacademy.familywallet.data.CATEGORY
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.ICON
import by.itacademy.familywallet.data.ID
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.data.TRANSACTION_TYPE
import by.itacademy.familywallet.data.UID
import by.itacademy.familywallet.databinding.FragmentNewCategoryBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.utils.Icons
import by.itacademy.familywallet.utils.UserUtils
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class NewCategoryFragment : BaseFragment<FragmentAdapter, BaseViewModel>(R.layout.fragment_new_category) {
    private lateinit var binding: FragmentNewCategoryBinding
    private val repo by inject<DataRepository>()
    private var item: UIModel.CategoryModel? = null

    override val viewModel by inject<BaseViewModel>()
    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(null, null) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (arguments != null) {
            item = UIModel.CategoryModel(
                id = arguments?.getString(ID),
                uid = arguments?.getString(UID),
                category = arguments?.getString(CATEGORY),
                type = arguments?.getString(TRANSACTION_TYPE),
                icon = if (arguments?.getInt(ICON)==0) Icons.getIcons()[0] else arguments?.getInt(ICON)!!
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
                val icon = if (item!!.icon == 0) Icons.getIcons()[0] else item!!.icon
                setImageDrawable(resources.getDrawable(icon, context.theme))
                setColorFilter(resources.getColor(R.color.primaryTextColor, context.theme))
                setOnClickListener { addFragment(IconChooseFragment.newInstance()) }
            }
            title.text = String.format(getString(R.string.new_category_tittle), if (item?.type!! == INCOMES) getString(R.string.income) else getString(R.string.expenses))
        }
    }

    private fun createNewCategory() {
        CoroutineScope(Dispatchers.IO).launch {
            val category = binding.itemName.text.toString()
            if (!category.isNullOrEmpty()) {
                repo.addNewCategory(
                    item!!.apply {
                        uid = UserUtils.getUsersUid()
                        this.category = binding.itemName.text.toString()
                        icon = if (item?.icon == 0) Icons.getIcons()[0] else item?.icon!!
                    }
                )
                withContext(Dispatchers.Main) {
                    onBack()
                }
            } else {
                withContext(Dispatchers.Main) { dialog.createNegativeDialog(context!!, getString(R.string.alert_negative_message_category_create)) }
            }
        }
    }

    override fun listenBus(wrapper: Any) {
        super.listenBus(wrapper)
        when (wrapper) {
            is IconWrapper -> with(binding.iconButton) {
                item?.icon = wrapper.iconId
                setImageDrawable(resources.getDrawable(wrapper.iconId, context.theme))
                setColorFilter(resources.getColor(R.color.primaryTextColor, context.theme))
            }
        }
    }

    private fun updateCategory() {
        with(item!!) {
            category = binding.itemName.text.toString()
            type = item?.type!!
        }
        CoroutineScope(Dispatchers.IO).launch {
            repo.upDateItem(item)
            withContext(Dispatchers.Main) {
                onBack()
            }
        }
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
                putInt(ICON, item.icon)
            }
        }
    }
}