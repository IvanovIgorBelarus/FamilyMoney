package by.itacademy.familywallet.features.start.presantation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.wrappers.DeleteOperationWrapper
import by.itacademy.familywallet.common.wrappers.SettingsChangeWrapper
import by.itacademy.familywallet.core.adapter.FragmentAdapter
import by.itacademy.familywallet.core.others.BANK
import by.itacademy.familywallet.core.others.BaseFragment
import by.itacademy.familywallet.core.others.EXPENSES
import by.itacademy.familywallet.databinding.FragmentStartBinding
import by.itacademy.familywallet.features.operations.presantation.CategoryOperationFragment
import by.itacademy.familywallet.features.start.view_model.StartFragmentViewModel
import by.itacademy.familywallet.features.transaction.presentation.TransactionFragment
import by.itacademy.familywallet.utils.PiePreparator
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import by.itacademy.familywallet.utils.toStringFormat
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class StartFragment : BaseFragment<FragmentAdapter, StartFragmentViewModel>(R.layout.fragment_start) {
    private lateinit var binding: FragmentStartBinding

    override val viewModel by inject<StartFragmentViewModel>()
    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(null, null) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStartBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            with(diagram) {
                setNoDataText("")
                description.isEnabled = false
                isRotationEnabled = false
                isClickable = false
                holeRadius = 10f
                setHoleColor(context.resources.getColor(R.color.tabBackgroundColor, context.theme))
                setEntryLabelColor(context.resources.getColor(R.color.textPieColor, context.theme))
                setTransparentCircleAlpha(0)
                legend.isEnabled = false
                setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                    override fun onNothingSelected() {}
                    override fun onValueSelected(e: Entry?, h: Highlight?) {
                        addFragment(CategoryOperationFragment.newInstance((e as PieEntry).label, EXPENSES))
                    }
                })
            }
            openBank.setOnClickListener {
                addFragment(TransactionFragment.newInstance(BANK, null))
            }

            viewModel.liveDataStart.observe(this@StartFragment, Observer { model ->
                expensesTextView.text = String.format("%s %.2f BYN", getString(R.string.spend), model.expenses)
                incomeTextView.text = String.format("%s %.2f BYN", getString(R.string.income_text), model.incomes)
                balanceTextView.text = String.format("%s %.2f BYN", getString(R.string.balance), model.balance)
                bankTextView.text = model.bankString
                PiePreparator.preparePie(diagram, model.pieData.orEmpty(), context!!)
                currencyView.text = model.currencyString
            })
            currencyTitle.text = String.format(getString(R.string.currency_title, Calendar.getInstance().timeInMillis.toStringFormat))
            isLoading.set(false)
        }
    }

    override fun listenBus(wrapper: Any) {
        if (wrapper is SettingsChangeWrapper || wrapper is DeleteOperationWrapper) {
            viewModel.getData()
        }
    }

    companion object {
        fun newInstance() = StartFragment()
    }
}