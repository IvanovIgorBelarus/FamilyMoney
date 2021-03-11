package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentStartBinding
import by.itacademy.familywallet.viewmodel.StartFragmentViewModel
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
            makeIncomeButton.setOnClickListener {
                // startActivity(TransactionActivity.start(this@StartFragment.context, INCOMES, null))  //пока не сделал возможность выбора категории внутри оплаты
            }
            makeCostButton.setOnClickListener {
                //  startActivity(TransactionActivity.start(this@StartFragment.context, EXPENSES, null))  //пока не сделал возможность выбора категории внутри оплаты
            }

            with(startFragmentViewModel) {
                liveDataExpenses.observe(this@StartFragment, Observer { expensesTextView.text = String.format("%s %s BYN", getString(R.string.spend), it) })
                liveDataIncomes.observe(this@StartFragment, Observer { incomeTextView.text = String.format("%s %s BYN", getString(R.string.income_text), it) })
                liveDataBalance.observe(this@StartFragment, Observer { balanceTextView.text = String.format("%s %s BYN", getString(R.string.balance), it) })
            }
        }
    }

    companion object {
        fun newInstance() = StartFragment()
    }
}