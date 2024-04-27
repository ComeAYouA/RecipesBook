package lithium.kotlin.recipesbook.core.model


data class Measures(
    val metric: Measure,
    val us: Measure
)

data class Measure(
    val amount: Double,
    val longName: String,
    val shortName: String
)