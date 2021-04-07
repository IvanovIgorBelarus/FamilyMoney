package by.itacademy.familywallet.view.fragment.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.BANK
import by.itacademy.familywallet.databinding.FragmentStartBinding
import by.itacademy.familywallet.utils.PiePreparator
import by.itacademy.familywallet.view.activity.FragmentsActivity
import by.itacademy.familywallet.view.fragment.CategoryOperationFragment
import by.itacademy.familywallet.view.fragment.TransactionFragment
import by.itacademy.familywallet.viewmodel.StartFragmentViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import org.koin.android.ext.android.inject

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding
    private val startFragmentViewModel by inject<StartFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStartBinding.bind(view)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        startFragmentViewModel.getTransactions()
    }

    private fun initViews() {
        with(binding) {
            with(diagram) {
                description.isEnabled = false
                isRotationEnabled = false
                isClickable = false
                holeRadius = 10f
                setEntryLabelColor(context.resources.getColor(R.color.textPieColor, context.theme))
                setTransparentCircleAlpha(0)
                legend.isEnabled = false
                setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                    override fun onNothingSelected() {}

                    override fun onValueSelected(e: Entry?, h: Highlight?) {
                        (activity as FragmentsActivity).screenManager.startFragment(CategoryOperationFragment.newInstance((e as PieEntry).label))
                    }
                })
            }
            openBank.setOnClickListener {
                (activity as FragmentsActivity).screenManager.startFragment(TransactionFragment.newInstance(BANK, null))
            }
            with(startFragmentViewModel) {
                liveDataExpenses.observe(this@StartFragment, Observer { expensesTextView.text = String.format("%s %.2f BYN", getString(R.string.spend), it) })
                liveDataIncomes.observe(this@StartFragment, Observer { incomeTextView.text = String.format("%s %.2f BYN", getString(R.string.income_text), it) })
                liveDataBalance.observe(this@StartFragment, Observer { balanceTextView.text = String.format("%s %.2f BYN", getString(R.string.balance), it) })
                liveDataDataBank.observe(this@StartFragment, Observer { bankTextView.text = it })
                liveDataPie.observe(this@StartFragment, Observer { PiePreparator.preparePie(diagram, it, context!!) })
            }
        }
    }

    companion object {
        fun newInstance() = StartFragment()
    }
}