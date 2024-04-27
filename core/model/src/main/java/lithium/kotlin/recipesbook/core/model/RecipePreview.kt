package lithium.kotlin.recipesbook.core.model

data class RecipePreview(
    val isBookmarked: Boolean = false,
    val id: Long,
    val tittle: String?,
    val imageUrl: String?,
    val cookingTimeMinutes: Int?,
    var dishTypes: List<DishType?>,
    val score: Double?
)