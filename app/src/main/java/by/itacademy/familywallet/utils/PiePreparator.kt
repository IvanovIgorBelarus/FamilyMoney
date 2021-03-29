package by.itacademy.familywallet.utils

import android.content.Context
import by.itacademy.familywallet.R
import by.itacademy.familywallet.model.PieModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

object PiePreparator {
    fun preparePie(pie: PieChart, data: List<PieModel>, context: Context) {
        val prepareList = data.sortedByDescending { it.value }
        var entrys = arrayListOf<PieEntry>()
        var othersValue = 0.0f
        for (i in 0..8) {
            entrys.add(PieEntry(prepareList[i].value, prepareList[i].category!!))
        }
        for (i in 9 until data.size) {
            othersValue += prepareList[i].value
        }
        entrys.add(PieEntry(othersValue, "остальные"))
        val pieDataSet = PieDataSet(entrys, null).apply {
            sliceSpace = 1f
            valueTextSize = 12f
            colors = getPieColors(context)
        }
        pie.data = PieData(pieDataSet)
        pie.invalidate()
    }

    private fun getPieColors(context: Context): List<Int> {
        var resultList = mutableListOf<Int>()
        resultList.add(context.resources.getColor(R.color.color1, context.theme))
        resultList.add(context.resources.getColor(R.color.color2, context.theme))
        resultList.add(context.resources.getColor(R.color.color3, context.theme))
        resultList.add(context.resources.getColor(R.color.color4, context.theme))
        resultList.add(context.resources.getColor(R.color.color5, context.theme))
        resultList.add(context.resources.getColor(R.color.color6, context.theme))
        resultList.add(context.resources.getColor(R.color.color7, context.theme))
        resultList.add(context.resources.getColor(R.color.color8, context.theme))
        resultList.add(context.resources.getColor(R.color.color9, context.theme))
        resultList.add(context.resources.getColor(R.color.color10, context.theme))
        resultList.add(context.resources.getColor(R.color.color11, context.theme))
        resultList.add(context.resources.getColor(R.color.color12, context.theme))

        return resultList
    }
}