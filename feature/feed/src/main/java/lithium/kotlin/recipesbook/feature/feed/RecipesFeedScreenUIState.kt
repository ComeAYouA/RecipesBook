package lithium.kotlin.recipesbook.feature.feed

import lithium.kotlin.recipesbook.core.model.Recipe

internal sealed interface RecipesFeedContentUiState {
    object Loading : RecipesFeedContentUiState
    data class Success(
        val data: List<Recipe>
    ) : RecipesFeedContentUiState
    data class Error(
        val message: String
    ) : RecipesFeedContentUiState
}

internal data class RecipeScreenUiState (
    val content: Content
)

enum class Content(val tittle: String){
    InterestingRecipes("Interesting Recipes"),
    FoundRecipes("Found Recipes"),
    FavoriteRecipes("Favorite Recipes")
}