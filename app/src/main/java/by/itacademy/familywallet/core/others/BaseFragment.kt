package by.itacademy.familywallet.core.others

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.wrappers.SmsWrapper
import by.itacademy.familywallet.common.wrappers.UpdateWrapper
import by.itacademy.familywallet.core.adapter.FragmentAdapter
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.features.start.presantation.FragmentsActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject

abstract class BaseFragment<AD : FragmentAdapter, VM : BaseViewModel>(private val layout: Int) : Fragment(), ItemOnLongClickListener {
    open val fragmentAdapter: AD? = null
    open val viewModel: VM? = null
    val dialog by inject<Dialogs>()
    private lateinit var parentActivity: FragmentsActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        parentActivity = (activity as FragmentsActivity)
        updateAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layout, container, false)

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onLongClick(item: Any?) {
        dialog.deleteDialog(item, this)
    }

    protected fun showActionBar(isShowing: Boolean) {
        if (isShowing) {
            parentActivity.supportActionBar?.show()
        } else {
            parentActivity.supportActionBar?.hide()
        }
    }

    protected fun hideKeyBoard() {
        val inputMethodManager: InputMethodManager = this.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = parentActivity.currentFocus
        if (view == null) {
            view = View(parentActivity)
        }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    open fun onBack() {
        hideKeyBoard()
        parentActivity.onBackPressed()
    }

    fun addFragment(fragment: Fragment) {
        parentActivity.screenManager.startFragment(fragment)
    }

    private fun updateAdapter() {
        viewModel?.liveData?.observe(this, Observer {
            fragmentAdapter?.update(it)
            checkDescribeVisibility(it.isEmpty())
        })
    }

    open fun checkDescribeVisibility(isShowing: Boolean) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun listenBus(wrapper: Any) {
        when (wrapper) {
            is SmsWrapper -> parentActivity.opMenu?.getItem(1)?.setIcon(R.drawable.ic_baseline_sms_failed)
            is UpdateWrapper -> viewModel?.getData()
        }
    }
}