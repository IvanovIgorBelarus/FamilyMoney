package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemOnLongClickListener
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.view.activity.FragmentsActivity
import by.itacademy.familywallet.view.fragment.CategoryOperationFragment
import by.itacademy.familywallet.viewmodel.BaseViewModel
import com.github.mikephil.charting.data.PieEntry
import org.koin.android.ext.android.inject

abstract class BaseFragment<AD : FragmentAdapter, VM : BaseViewModel>(private val layout: Int) : Fragment(), ItemOnLongClickListener {
    protected abstract val fragmentAdapter: AD
    protected abstract val viewModel: VM
    protected val dialog by inject<Dialogs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layout, container, false)

    open fun updateAdapter() {
        viewModel.liveData.observe(this, Observer { fragmentAdapter.update(it) })
    }

    override fun onLongClick(item: Any?) {
        dialog.deleteDialog(item, this)
        this.onResume()
    }

    fun onBack() {
        (activity as FragmentsActivity).onBackPressed()
    }

    protected fun showActionBar(isShowing: Boolean) {
        if (isShowing) {
            (activity as FragmentsActivity).supportActionBar?.show()
        } else {
            (activity as FragmentsActivity).supportActionBar?.hide()
        }
    }

    fun addFragment(fragment: Fragment){
        (activity as FragmentsActivity).screenManager.startFragment(fragment)
    }
}