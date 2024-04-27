package lithium.kotlin.recipesbook.core.model

data class RecipeInformation(
    val recipeId: Long,
    val tittle: String?,
    val imageUrl: String?,
    val readyInMinutes: Int?,
    val dishTypes: List<DishType?>,
    val score: Double?,
    val ingredients: List<Ingredient>,
)
