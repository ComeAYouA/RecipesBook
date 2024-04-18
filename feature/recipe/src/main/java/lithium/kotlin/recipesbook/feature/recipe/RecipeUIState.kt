package lithium.kotlin.recipesbook.feature.recipe

import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.RecipeInformation

sealed interface RecipeUIState {
    object Loading : RecipeUIState
    data class Success(
        val data: RecipeInformation
    ) : RecipeUIState
    data class Error(
        val message: String
    ) : RecipeUIState
}