package lithium.kotlin.recipesbook.core.model

data class Ingredient(
    val id: Long,
    val name: String,
    val amount: Double,
    val unit: String,
    val image: String
)
