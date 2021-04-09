package by.itacademy.familywallet.view.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.IconWrapper
import by.itacademy.familywallet.databinding.FragmentTypeTransactionBinding
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.viewmodel.BaseViewModel
import by.itacademy.familywallet.viewmodel.IconChooseViewModel
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class IconChooseFragment : BaseFragment<FragmentAdapter, BaseViewModel>(R.layout.fragment_type_transaction),ItemClickListener {

    private lateinit var binding: FragmentTypeTransactionBinding
    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(this as ItemClickListener, null) }
    override val viewModel: BaseViewModel by viewModel<IconChooseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTypeTransactionBinding.bind(view)
        initViews()
        viewModel.getData()
        updateAdapter()
    }

    private fun initViews(){
        with(binding){
            adapterRv.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = fragmentAdapter
            }
            categoryCreateButton.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance() = IconChooseFragment()
    }

    override fun onClick(item: Any?) {
        EventBus.getDefault().post(IconWrapper(item as Int))
        onBack()
    }
}