package by.itacademy.familywallet.presentation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.itacademy.familywallet.data.EXPENSES
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.view.OperationsFragment
import by.itacademy.familywallet.view.StartFragment
import by.itacademy.familywallet.view.StatisticFragment
import by.itacademy.familywallet.view.TypeTransactionFragment


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