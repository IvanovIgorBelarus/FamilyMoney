package by.itacademy.familywallet.utils

import android.text.Editable
import android.text.TextWatcher

class DateMask : TextWatcher {
    private var updatedText: String? = null
    private var edit = false
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (edit) return
        updatedText = s.toString().replace("\\D".toRegex(), "")
    }

    override fun afterTextChanged(editable: Editable) {
        if (edit) return
        edit = true
        when (updatedText?.length) {
            2 -> {
                val day = updatedText?.substring(0, 2)
                updatedText = String.format("%s/", day)
            }
            4 -> {
                val day = updatedText?.substring(0, 2)
                val month = updatedText?.substring(2, 4)
                updatedText = String.format("%s/%s/", day, month)
            }
            8 -> {
                val day = updatedText?.substring(0, 2)
                val month = updatedText?.substring(2, 4)
                val year = updatedText?.substring(4)
                updatedText = String.format("%s/%s/%s", day, month, year)
            }
        }
        editable.clear()
        editable.insert(0, updatedText)
        edit = false
    }
}