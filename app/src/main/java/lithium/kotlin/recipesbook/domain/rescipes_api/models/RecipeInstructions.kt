package lithium.kotlin.recipesbook.domain.rescipes_api.models

import lithium.kotlin.recipesbook.domain.models.Recipe

data class RecipeInstructions(
    val steps: List<Recipe>
)
