package by.itacademy.familywallet.presentation

interface ItemClickListener {
    fun onClick(transactionType: String)
    fun onLongClick(transactionType: String, item: Char)
}