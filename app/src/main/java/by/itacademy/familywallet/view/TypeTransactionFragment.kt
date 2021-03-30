package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.App
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.databinding.FragmentTypeTransactionBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.ItemOnLongClickListener
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.viewmodel.TypeTransactionViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TypeTransactionFragment : Fragment(), ItemClickListener, ItemOnLongClickListener {
    private lateinit var fragmentType: String
    private lateinit var binding: FragmentTypeTransactionBinding
    private val fragmentAdapter: FragmentAdapter by inject { parametersOf(this as ItemClickListener, this as ItemOnLongClickListener) }
    private val transactionViewModel by viewModel<TypeTransactionViewModel>()
    private val dialog by inject<Dialogs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_type_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTypeTransactionBinding.bind(view)
        binding.adapterRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fragmentAdapter
        }
        initCreateButton()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        transactionViewModel.getTransactionTypeList(fragmentType)
    }

    private fun initViewModel() {
        transactionViewModel.liveData.observe(this, Observer { list -> fragmentAdapter.update(list) })
    }

    private fun initCreateButton() {
        with(binding) {
            with(categoryCreateButton) {
                setOnClickListener {
                    startActivity(TransactionSettingsActivity.start(this.context, fragmentType))
                }
                App().viewPreparation.prepareView(categoryCreateButton, fragmentType)
            }
        }
    }

    override fun onClick(item: Any?) {
        //startActivity(TransactionActivity.start(this.context, (item as UIModel.CategoryModel)?.type, item?.category))
        (activity as FragmentsActivity).screenManager.startFragment(TransactionFragment.getInstance((item as UIModel.CategoryModel)?.type, item?.category))
    }

    companion object {
        fun newInstance(type: String) = TypeTransactionFragment().apply { fragmentType = type }
    }

    override fun onLongClick(item: Any?) {
        dialog.deleteDialog(item, this)
        this.onResume()
    }
}