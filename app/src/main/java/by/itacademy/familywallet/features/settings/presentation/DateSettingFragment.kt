package by.itacademy.familywallet.features.settings.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.features.start.App
import by.itacademy.familywallet.features.start.App.Companion.dateFilterType
import by.itacademy.familywallet.features.start.App.Companion.endDate
import by.itacademy.familywallet.features.start.App.Companion.startDate
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.wrappers.SettingsChangeWrapper
import by.itacademy.familywallet.core.others.DAY_FILTER
import by.itacademy.familywallet.core.others.FULL_DATE
import by.itacademy.familywallet.core.others.INCOMES
import by.itacademy.familywallet.core.others.MONTH_FILTER
import by.itacademy.familywallet.core.others.NO_DATE_FILTER
import by.itacademy.familywallet.core.others.RANGE_FILTER
import by.itacademy.familywallet.core.others.WEEK_FILTER
import by.itacademy.familywallet.databinding.FragmentDateSettingBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.core.adapter.FragmentAdapter
import by.itacademy.familywallet.core.others.ItemClickListener
import by.itacademy.familywallet.utils.formatDate
import by.itacademy.familywallet.core.others.BaseFragment
import by.itacademy.familywallet.features.settings.viewmodel.DateSettingsViewModel
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class DateSettingFragment : BaseFragment<FragmentAdapter, DateSettingsViewModel>(R.layout.fragment_date_setting), ItemClickListener {
    private lateinit var binding: FragmentDateSettingBinding

    override val viewModel by viewModel<DateSettingsViewModel>()
    override val fragmentAdapter: FragmentAdapter by inject { parametersOf(this as ItemClickListener, null) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDateSettingBinding.bind(view)
        showActionBar(false)
        enabledButton()
        initViews()
    }

    private fun initViews() {
        with(binding) {
            noFilterButton.setOnClickListener {
                dateFilterType = NO_DATE_FILTER
                onBack()
            }
            monthFilterButton.setOnClickListener {
                dateFilterType = MONTH_FILTER
                onBack()
            }
            weekFilterButton.setOnClickListener {
                dateFilterType = WEEK_FILTER
                onBack()
            }
            dayFilterButton.setOnClickListener {
                dateFilterType = DAY_FILTER
                onBack()
            }

            rangeFilterButton.setOnClickListener {
                if (startDate != null && endDate != null && startDate!! < endDate!!) {
                    dateFilterType = RANGE_FILTER
                    onBack()
                } else {
                    dialog.createNegativeDialog(context!!, getString(R.string.alert_negative_date_range_message))
                }
            }
            with(startDateTextView) {
                if (startDate != null) {
                    text = Date(startDate!!).formatDate(FULL_DATE)
                }
                setOnClickListener { dialog.createDateDialog(context!!, startDateTextView) }
            }
            with(endDateTextView) {
                if (endDate != null) {
                    text = Date(endDate!!).formatDate(FULL_DATE)
                }
                setOnClickListener { dialog.createDateDialog(context!!, endDateTextView) }
            }

            adapterRv.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = fragmentAdapter
            }
        }
    }

    private fun enabledButton() {
        with(binding) {
            when (dateFilterType) {
                NO_DATE_FILTER -> {
                    noFilterButton.isEnabled = false
                    App().viewPreparation.prepareView(noFilterButton, INCOMES)
                    monthFilterButton.isEnabled = true
                    weekFilterButton.isEnabled = true
                    dayFilterButton.isEnabled = true
                    startDate = null
                    endDate = null
                }
                MONTH_FILTER -> {
                    noFilterButton.isEnabled = true
                    monthFilterButton.isEnabled = false
                    App().viewPreparation.prepareView(monthFilterButton, INCOMES)
                    weekFilterButton.isEnabled = true
                    dayFilterButton.isEnabled = true
                    startDate = null
                    endDate = null
                }
                WEEK_FILTER -> {
                    noFilterButton.isEnabled = true
                    monthFilterButton.isEnabled = true
                    weekFilterButton.isEnabled = false
                    App().viewPreparation.prepareView(weekFilterButton, INCOMES)
                    dayFilterButton.isEnabled = true
                    startDate = null
                    endDate = null
                }
                DAY_FILTER -> {
                    noFilterButton.isEnabled = true
                    monthFilterButton.isEnabled = true
                    weekFilterButton.isEnabled = true
                    dayFilterButton.isEnabled = false
                    App().viewPreparation.prepareView(dayFilterButton, INCOMES)
                    startDate = null
                    endDate = null
                }
            }
        }
    }

    override fun onClick(item: Any?) {
        startDate = (item as UIModel.ArchiveMonthModel).startDate
        endDate = item.endDate
        dateFilterType = RANGE_FILTER
        onBack()
    }

    override fun onBack() {
        EventBus.getDefault().post(SettingsChangeWrapper())
        super.onBack()
    }
    companion object {
        fun newInstance() = DateSettingFragment()
    }
}