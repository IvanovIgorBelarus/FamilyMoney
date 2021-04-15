package by.itacademy.familywallet.view.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.IconWrapper
import by.itacademy.familywallet.databinding.FragmentIconChooseBinding
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.viewmodel.BaseViewModel
import by.itacademy.familywallet.viewmodel.IconChooseViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class IconChooseFragment : BaseFragment<FragmentAdapter, BaseViewModel>(R.layout.fragment_icon_choose) {

    private lateinit var binding: FragmentIconChooseBinding

    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(null, null) }
    override val viewModel by viewModel<IconChooseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIconChooseBinding.bind(view)
        binding.adapterRv.apply {
            layoutManager = GridLayoutManager(context, 5)
            adapter = fragmentAdapter
        }
        viewModel.getData()
        updateAdapter()
    }

    companion object {
        fun newInstance() = IconChooseFragment()
    }

    override fun listenBus(wrapper: Any) {
        super.listenBus(wrapper)
        when (wrapper) {
            is IconWrapper -> onBack()
        }
    }
}