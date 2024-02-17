package lithium.kotlin.recipesbook.domain.models

import lithium.kotlin.recipesbook.domain.models.RecipeResource

data class RandomRecipesResponse(
    val recipes: List<RecipeResource>
)
