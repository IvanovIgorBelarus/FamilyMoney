package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.App
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.data.TRANSACTION_TYPE
import by.itacademy.familywallet.databinding.FragmentNewCategoryBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.UserUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class NewCategoryFragment : Fragment() {
    private lateinit var binding: FragmentNewCategoryBinding
    private val repo by inject<DataRepository>()
    private var transactionType: String? = null
    private val dialog by inject<Dialogs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_new_category, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as FragmentsActivity).supportActionBar?.hide()
        binding = FragmentNewCategoryBinding.bind(view)
        transactionType = arguments?.getString(TRANSACTION_TYPE)
        if (transactionType != null) {
            initViews()
        }
    }

    private fun initViews() {
        with(binding) {
            with(saveButton) {
                App().viewPreparation.prepareView(this, transactionType!!)
                setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        val category = binding.itemName.text.toString()
                        if (!category.isNullOrEmpty()) {
                            repo.addNewCategory(
                                UIModel.CategoryModel(
                                    uid = UserUtils.getUsersUid(),
                                    category = binding.itemName.text.toString(),
                                    type = transactionType!!
                                )
                            )
                            withContext(Dispatchers.Main) {
                                (activity as FragmentsActivity).onBackPressed()
                            }
                        } else {
                            withContext(Dispatchers.Main) { dialog.createNegativeDialog(context, getString(R.string.alert_negative_message_category_create)) }
                        }
                    }
                }
            }
            App().viewPreparation.prepareView(itemName, transactionType!!)
        }
    }


    companion object {
        fun newInstance(transactionType: String) = NewCategoryFragment().apply {
            arguments = Bundle().apply {
                putString(TRANSACTION_TYPE, transactionType)
            }
        }
    }
}