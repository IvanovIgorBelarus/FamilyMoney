package by.itacademy.familywallet.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.CATEGORY
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.ICON
import by.itacademy.familywallet.data.ID
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.data.TRANSACTION_TYPE
import by.itacademy.familywallet.data.UID
import by.itacademy.familywallet.databinding.FragmentNewCategoryBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.Icons
import by.itacademy.familywallet.utils.UserUtils
import by.itacademy.familywallet.view.activity.FragmentsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class NewCategoryFragment : Fragment() {
    private lateinit var binding: FragmentNewCategoryBinding
    private val repo by inject<DataRepository>()
    private var item: UIModel.CategoryModel? = null
    private val dialog by inject<Dialogs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_new_category, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as FragmentsActivity).supportActionBar?.hide()
        binding = FragmentNewCategoryBinding.bind(view)
        if (arguments != null) {
            item = UIModel.CategoryModel(
                id = arguments?.getString(ID),
                uid = arguments?.getString(UID),
                category = arguments?.getString(CATEGORY),
                type = arguments?.getString(TRANSACTION_TYPE),
                icon = arguments?.getLong(ICON)
            )
            initViews()
        }
    }

    private fun initViews() {
        with(binding) {
            itemName.setText(item?.category ?: "")
            with(saveButton) {
                if (item?.category.isNullOrEmpty()) {
                    createNewCategory()
                } else {

                }
            }
            with(iconTextView) {
                setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(item?.icon?.toInt() ?: Icons.getIcons()[0], context.theme), null, null, null)
                compoundDrawableTintList =
                    android.content.res.ColorStateList.valueOf(androidx.core.content.ContextCompat.getColor(context, R.color.primaryTextColor))
            }
            selectIconButton.setOnClickListener { (activity as FragmentsActivity).screenManager.startFragment(IconChooseFragment.newInstance()) }
            title.text = String.format(getString(R.string.new_category_tittle), if (item?.type!! == INCOMES) getString(R.string.income) else getString(R.string.expenses))
        }
    }

    private fun createNewCategory() {
        CoroutineScope(Dispatchers.IO).launch {
            val category = binding.itemName.text.toString()
            if (!category.isNullOrEmpty()) {
                repo.addNewCategory(
                    UIModel.CategoryModel(
                        uid = UserUtils.getUsersUid(),
                        category = binding.itemName.text.toString(),
                        type = item?.type!!
                    )
                )
                withContext(Dispatchers.Main) {
                    (activity as FragmentsActivity).onBackPressed()
                }
            } else {
                withContext(Dispatchers.Main) { dialog.createNegativeDialog(context!!, getString(R.string.alert_negative_message_category_create)) }
            }
        }
    }

    private fun upDateCategory(){
        CoroutineScope(Dispatchers.IO).launch {
            repo.upDateItem(item)
            withContext(Dispatchers.Main) {
                (activity as FragmentsActivity).onBackPressed()
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
                putLong(ICON, item.icon ?: -1)
            }
        }
    }
}