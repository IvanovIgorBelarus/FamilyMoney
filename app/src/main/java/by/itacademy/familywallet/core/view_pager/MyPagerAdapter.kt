package by.itacademy.familywallet.core.view_pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.itacademy.familywallet.core.others.EXPENSES
import by.itacademy.familywallet.core.others.INCOMES
import by.itacademy.familywallet.features.history.presentation.OperationsFragment
import by.itacademy.familywallet.features.start.presantation.StartFragment
import by.itacademy.familywallet.features.statistics.presentation.StatisticFragment
import by.itacademy.familywallet.features.transactions_list.presentation.TypeTransactionFragment


class MyPagerAdapter(fa: Fragment) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = createFragmentList().size
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StartFragment.newInstance()
            1 -> TypeTransactionFragment.newInstance(EXPENSES)
            2 -> TypeTransactionFragment.newInstance(INCOMES)
            3 -> OperationsFragment.newInstance()
            4 -> StatisticFragment.newInstance()
            else ->
                return StartFragment.newInstance()
        }
    }

    private fun createFragmentList(): List<Fragment> = mutableListOf<Fragment>().apply {
        add(StartFragment.newInstance())
        add(TypeTransactionFragment.newInstance(EXPENSES))
        add(TypeTransactionFragment.newInstance(INCOMES))
        add(OperationsFragment.newInstance())
        add(StatisticFragment.newInstance())
    }
}