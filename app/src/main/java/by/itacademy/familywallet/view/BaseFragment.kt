package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.presentation.ItemOnLongClickListener
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.view.activity.FragmentsActivity
import by.itacademy.familywallet.viewmodel.BaseViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject

abstract class BaseFragment<AD : FragmentAdapter, VM : BaseViewModel>(private val layout: Int) : Fragment(), ItemOnLongClickListener {
    open val fragmentAdapter: AD? = null
    open val viewModel: VM? = null
    val dialog by inject<Dialogs>()
    val repo by inject<DataRepository>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layout, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    open fun updateAdapter() {
        viewModel?.liveData?.observe(this, Observer { fragmentAdapter?.update(it) })
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

    fun addFragment(fragment: Fragment) {
        (activity as FragmentsActivity).screenManager.startFragment(fragment)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun listenBus(wrapper: Any) {
    }
}