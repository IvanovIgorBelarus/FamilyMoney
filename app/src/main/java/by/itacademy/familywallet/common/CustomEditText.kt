package by.itacademy.familywallet.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.CustomEditTextBinding

class CustomEditText : FrameLayout {

    private val binding = CustomEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    var background: Int = -1
        set(value) {
            field = value
            if (value > 0) {
                binding.rootView.setBackgroundResource(value)
            }
        }
    var categoryImage: Int = -1
        set(value) {
            field = value
            if (value > 0) {
                val drawable = ContextCompat.getDrawable(context, value)
                binding.iconButton.setImageDrawable(drawable)
                binding.iconButton.setColorFilter(resources.getColor(R.color.primaryTextColor, context.theme))
            }
        }
    var saveImage: Int = -1
        set(value) {
            field = value
            if (value > 0) {
                val drawable = ContextCompat.getDrawable(context, value)
                binding.saveButton.setImageDrawable(drawable)
            }
        }
    var text: String = ""
        set(value) {
            field = value
            binding.itemName.setText(value)
        }
        get() = binding.itemName.text.toString()

    var hint: String = ""
        set(value) {
            field = value
            binding.itemName.hint = value
        }

    var imageClickListener: (() -> Unit)? = null
    var saveClickListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }


    private fun init(attrs: AttributeSet?) {
        with(binding) {
            iconButton.setOnClickListener { imageClickListener?.invoke() }
            saveButton.setOnClickListener { saveClickListener?.invoke() }
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomEditText, 0, 0).apply {
                try {
                    background = getResourceId(R.styleable.CustomEditText_editBackground, -1)
                    categoryImage = getResourceId(R.styleable.CustomEditText_categoryImage, -1)
                    saveImage = getResourceId(R.styleable.CustomEditText_saveImage, -1)
                    text = getString(R.styleable.CustomEditText_text).orEmpty()
                    hint = getString(R.styleable.CustomEditText_hint).orEmpty()
                } finally {
                    recycle()
                }
            }
        }
    }
}