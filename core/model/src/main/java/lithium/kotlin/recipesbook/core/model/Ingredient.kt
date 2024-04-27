package lithium.kotlin.recipesbook.core.model

data class Ingredient(
    val id: Int,
    val name: String,
    val amount: Double,
    val unit: String,
    val image: String,
    val measures: Measures
)
