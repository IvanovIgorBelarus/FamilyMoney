package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.SmsWrapper
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
    private lateinit var parentActivity: FragmentsActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        parentActivity = (activity as FragmentsActivity)
        viewModel?.getData()
        updateAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layout, container, false)

    override fun onResume() {
        super.onResume()
        viewModel?.getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun updateAdapter() {
        viewModel?.liveData?.observe(this, Observer {
            fragmentAdapter?.update(it)
            checkDescribeVisibility(it.isEmpty())
        })
    }

    override fun onLongClick(item: Any?) {
        dialog.deleteDialog(item, this)
        this.onResume()
    }

    fun onBack() {
        parentActivity.onBackPressed()
    }

    protected fun showActionBar(isShowing: Boolean) {
        if (isShowing) {
            parentActivity.supportActionBar?.show()
        } else {
            parentActivity.supportActionBar?.hide()
        }
    }

    fun addFragment(fragment: Fragment) {
        parentActivity.screenManager.startFragment(fragment)
    }

    open fun checkDescribeVisibility(isShowing: Boolean) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun listenBus(wrapper: Any) {
        when (wrapper) {
            is SmsWrapper -> parentActivity.opMenu?.getItem(1)?.setIcon(R.drawable.ic_baseline_sms_failed)
        }
    }
}