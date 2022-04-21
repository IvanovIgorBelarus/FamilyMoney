package by.itacademy.familywallet.model

class StartModel(
    var expenses: Double? = null,
    var incomes: Double? = null,
    var balance: Double? = null,
    var bankString: String? = null,
    var pieData: List<PieModel>? = null,
    var currencyString: String? = null
)