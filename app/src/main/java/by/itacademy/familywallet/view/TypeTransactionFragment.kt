package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentTypeTransactionBinding
import by.itacademy.familywallet.model.CategoryModel
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.TypeTransactionAdapter
import by.itacademy.familywallet.viewmodel.TypeTransactionViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TypeTransactionFragment : Fragment(), ItemClickListener {
    private lateinit var fragmentType: String
    private lateinit var binding: FragmentTypeTransactionBinding
    private val typeTransactionAdapter: TypeTransactionAdapter by inject {
        parametersOf(
            this,
            fragmentType
        )
    }
    private val transactionViewModel by viewModel<TypeTransactionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_type_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTypeTransactionBinding.bind(view)
        binding.typeAdapter.apply {
            layoutManager = LinearLayoutManager(this@TypeTransactionFragment.context)
            adapter = typeTransactionAdapter
        }
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        transactionViewModel.getTransactionTypeList(fragmentType)
    }

    private fun initViewModel() {
        transactionViewModel.liveData.observe(this, { list ->
            typeTransactionAdapter.update(list)
        })
    }

    override fun onClick(item: CategoryModel) {
        startActivity(TransactionActivity.start(this.context, item.type, item.category))
    }

    override fun onLongClick(transactionType: String, item: CategoryModel) {
        startActivity(TransactionSettingsActivity.start(this.context, transactionType, item))
    }

    companion object {
        fun newInstance(type: String) = TypeTransactionFragment().apply { fragmentType = type }
    }
}