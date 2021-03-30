package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.App
import by.itacademy.familywallet.App.Companion.dateFilterType
import by.itacademy.familywallet.App.Companion.endDate
import by.itacademy.familywallet.App.Companion.startDate
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.DAY_FILTER
import by.itacademy.familywallet.data.FULL_DATE
import by.itacademy.familywallet.data.INCOMES
import by.itacademy.familywallet.data.MONTH_FILTER
import by.itacademy.familywallet.data.NO_DATE_FILTER
import by.itacademy.familywallet.data.RANGE_FILTER
import by.itacademy.familywallet.data.WEEK_FILTER
import by.itacademy.familywallet.databinding.FragmentDateSettingBinding
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.formatDate
import org.koin.android.ext.android.inject
import java.util.*

class DateSettingFragment : Fragment() {
    private lateinit var binding: FragmentDateSettingBinding
    private val dialog by inject<Dialogs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_date_setting, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDateSettingBinding.bind(view)
        enabledButton()
        initViews()
    }

    private fun initViews() {
        with(binding) {
            noFilterButton.setOnClickListener {
                dateFilterType = NO_DATE_FILTER
                (activity as FragmentsActivity).onDataSetChange(0)
            }
            monthFilterButton.setOnClickListener {
                dateFilterType = MONTH_FILTER
                (activity as FragmentsActivity).onDataSetChange(0)
            }
            weekFilterButton.setOnClickListener {
                dateFilterType = WEEK_FILTER
                (activity as FragmentsActivity).onDataSetChange(0)
            }
            dayFilterButton.setOnClickListener {
                dateFilterType = DAY_FILTER
                (activity as FragmentsActivity).onDataSetChange(0)
            }

            rangeFilterButton.setOnClickListener {
                if (startDate != null && endDate != null && startDate!! < endDate!!) {
                    dateFilterType = RANGE_FILTER
                    (activity as FragmentsActivity).onDataSetChange(0)
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

    companion object {
        fun newInstance() = DateSettingFragment()
    }
}