package by.itacademy.familywallet.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import by.itacademy.familywallet.R
import by.itacademy.familywallet.common.IconWrapper
import by.itacademy.familywallet.databinding.IconRecyclerItemBinding
import org.greenrobot.eventbus.EventBus

class IconViewHolder(
    private val binding: IconRecyclerItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Int) {
        binding.icon.apply {
            setImageDrawable(resources.getDrawable(item, context.theme))
            setColorFilter(resources.getColor(R.color.primaryTextColor, context.theme))
            setBackgroundResource(R.drawable.primary_rectangle_button_background)
            setOnClickListener {
                EventBus.getDefault().post(IconWrapper(item))
            }
        }
    }
}