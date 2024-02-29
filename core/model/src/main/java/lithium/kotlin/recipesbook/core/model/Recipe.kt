package lithium.kotlin.recipesbook.core.model

data class Recipe(
    val isBookmarked: Boolean = false,
    val id: Long,
    val tittle: String?,
    val imageUrl: String?,
    val cookingTimeMinutes: Int?,
    var dishTypes: List<DishType?>,
    val score: Double?
)