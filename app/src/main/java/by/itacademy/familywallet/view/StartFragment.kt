package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentStartBinding
import by.itacademy.familywallet.di.EXPENSES
import by.itacademy.familywallet.di.INCOMES

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStartBinding.bind(view)
        with(binding) {
            makeIncomeButton.setOnClickListener {
                startActivity(
                    TransactionActivity.startTransactionActivity(
                        this@StartFragment.context,
                        INCOMES
                    )
                )
            }
            makeCostButton.setOnClickListener {
                startActivity(
                    TransactionActivity.startTransactionActivity(
                        this@StartFragment.context,
                        EXPENSES
                    )
                )
            }
        }
    }

    companion object {
        fun newInstance() = StartFragment()
    }
}