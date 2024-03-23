package lithium.kotlin.recipesbook.feature.feed

import lithium.kotlin.recipesbook.core.model.Recipe

internal sealed interface RecipesFeedUiState {
    object Loading : RecipesFeedUiState
    data class Success(
        val data: List<Recipe>
    ) : RecipesFeedUiState
    data class Error(
        val message: String
    ) : RecipesFeedUiState
}