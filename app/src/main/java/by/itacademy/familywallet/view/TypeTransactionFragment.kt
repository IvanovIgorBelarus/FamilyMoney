package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.databinding.FragmentTypeTransactionBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.viewmodel.TypeTransactionViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TypeTransactionFragment : Fragment(), ItemClickListener {
    private lateinit var fragmentType: String
    private lateinit var binding: FragmentTypeTransactionBinding
    private val fragmentAdapter: FragmentAdapter by inject { parametersOf(this) }
    private val transactionViewModel by viewModel<TypeTransactionViewModel>()

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

    override fun onStart() {
        super.onStart()
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
                when (fragmentType) {
                    EXPENSES -> {
                        setTextColor(ContextCompat.getColor(context, R.color.red))
                        setBackgroundResource(R.drawable.costs_button_background)
                    }
                    INCOMES -> {
                        setTextColor(ContextCompat.getColor(context, R.color.green))
                        setBackgroundResource(R.drawable.income_button_background)
                    }
                }
            }
        }
    }

    override fun onClick(item: UIModel.CategoryModel?) {
        startActivity(TransactionActivity.start(this.context, item?.type, item?.category))
    }

    companion object {
        fun newInstance(type: String) = TypeTransactionFragment().apply { fragmentType = type }
    }
}