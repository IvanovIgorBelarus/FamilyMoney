package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentOperationBinding
import by.itacademy.familywallet.databinding.FragmentStatisticsBinding
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemOnLongClickListener
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.viewmodel.OperationsViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class OperationsFragment : Fragment(), ItemOnLongClickListener {
    private val operationsViewModel by viewModel<OperationsViewModel>()
    private lateinit var binding: FragmentOperationBinding
    private val fragmentAdapter: FragmentAdapter by inject { parametersOf(null, this as ItemOnLongClickListener) }
    private val dialog by inject<Dialogs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_operation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOperationBinding.bind(view)
        operationsViewModel.liveData.observe(this, Observer { list -> fragmentAdapter.update(list) })
        binding.adapterRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fragmentAdapter
        }
        binding.viewModel=operationsViewModel
        binding.executePendingBindings()
    }

    override fun onResume() {
        super.onResume()
        operationsViewModel.getAllTransactions()
    }

    companion object {
        @JvmStatic
        fun newInstance() = OperationsFragment()
    }

    override fun onLongClick(item: Any?) {
        dialog.deleteDialog(item, this)
        this.onResume()
    }
}