package by.itacademy.familywallet.model

sealed class UIModel{
    class CategoryModel (
        var category: String?,
        var type: String?
    )
}