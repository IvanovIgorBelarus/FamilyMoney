package by.itacademy.familywallet.utils

import android.content.Context
import by.itacademy.familywallet.R
import by.itacademy.familywallet.model.PieModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

object PiePreparator {
    private var otherCategories = mutableListOf<String?>()
    fun getOtherCategories(): List<String?> = otherCategories
    fun preparePie(pie: PieChart, data: List<PieModel>, context: Context) {
        pie.data = PieData(getDataSet(data, context))
        pie.invalidate()
    }

    private fun getDataSet(data: List<PieModel>, context: Context): PieDataSet {
        val prepareList = data.sortedByDescending { it.value }
        var entrys = arrayListOf<PieEntry>()
        var othersValue = 0.0f
        var count = 0
        for (i in 0..8) {
            if (prepareList[i].value >= 1.0f) {  //затраты со значением меньше одного процента не отображаются на пироге
                entrys.add(PieEntry(prepareList[i].value, prepareList[i].category!!))
                count++
            }
        }
        for (i in 9 until data.size) {
            otherCategories.add(prepareList[i].category)
            othersValue += prepareList[i].value
        }
        if (othersValue >= 1f) { //затраты со значением меньше одного процента не отображаются на пироге
            entrys.add(PieEntry(othersValue, "остальные"))
            count++
        }
        return PieDataSet(entrys, null).apply {
            valueTextColor = context.resources.getColor(R.color.textPieColor, context.theme)
            sliceSpace = 1f
            valueTextSize = 12f
            colors = getPieColors(context, count)
        }
    }

    private fun getPieColors(context: Context, count: Int): List<Int> {
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
        return resultList.subList(0, count)
    }
}