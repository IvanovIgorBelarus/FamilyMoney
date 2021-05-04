package by.itacademy.familywallet.view.fragment.viewpager

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.databinding.FragmentStartBinding
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.utils.PiePreparator
import by.itacademy.familywallet.utils.toStringFormat
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.view.fragment.CategoryOperationFragment
import by.itacademy.familywallet.view.fragment.TransactionFragment
import by.itacademy.familywallet.viewmodel.StartFragmentViewModel
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
            with(viewModel) {
                liveDataExpenses.observe(this@StartFragment, Observer { expensesTextView.text = String.format("%s %.2f BYN", getString(R.string.spend), it) })
                liveDataIncomes.observe(this@StartFragment, Observer { incomeTextView.text = String.format("%s %.2f BYN", getString(R.string.income_text), it) })
                liveDataBalance.observe(this@StartFragment, Observer { balanceTextView.text = String.format("%s %.2f BYN", getString(R.string.balance), it) })
                liveDataDataBank.observe(this@StartFragment, Observer { bankTextView.text = it })
                liveDataPie.observe(this@StartFragment, Observer { PiePreparator.preparePie(diagram, it, context!!) })
                liveDataCurrency.observe(this@StartFragment, Observer { currencyView.text=it })
            }
            currencyTitle.text= String.format(getString(R.string.currency_title, Calendar.getInstance().timeInMillis.toStringFormat))
        }
    }

    companion object {
        fun newInstance() = StartFragment()
    }
}