package by.itacademy.familywallet.viewmodel

import android.view.ViewGroup.LayoutParams
import android.widget.ImageButton
import android.widget.TableRow
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.IconWrapper
import by.itacademy.familywallet.databinding.FragmentIconChooseBinding
import by.itacademy.familywallet.utils.Icons
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading
import by.itacademy.familywallet.view.BaseFragment
import org.greenrobot.eventbus.EventBus

class IconChooseViewModel : BaseViewModel() {

    override fun getData() {
        isLoading.set(true)
        mutableLiveData.value = Icons.getIcons()
        isLoading.set(false)
    }

    fun createTab(fragment: BaseFragment<*, *>, binding: FragmentIconChooseBinding) {
        val context = fragment.context!!
        val tableLayoutCount = Icons.getIcons().size / 3
        var count = 0
        for (i in 0..tableLayoutCount) {
            val tableRow = TableRow(context).apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            }
            for (j in 0..8) {
                val icon = Icons.getIcons()[count]
                val imageButton = ImageButton(context).apply {
                    setImageDrawable(resources.getDrawable(icon, context.theme))
                    setColorFilter(resources.getColor(R.color.primaryTextColor, context.theme))
                    setBackgroundResource(R.drawable.primary_rectangle_button_background)
                    setOnClickListener {
                        EventBus.getDefault().post(IconWrapper(icon))
                        fragment.onBack()
                    }
                }
                tableRow.addView(imageButton, j)
                count++
                if (count == Icons.getIcons().size) return
            }
            binding.tabLayout.addView(tableRow, i)
            // if (count == -1) return
        }
    }
}