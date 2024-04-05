package lithium.kotlin.recipesbook.core.domain

import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Filter
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject

class LoadRandomRecipesUseCase @Inject constructor(
    private val recipesApi: RecipesRepository
) {
    suspend operator fun invoke(filters: List<Filter>): Result<List<Recipe>> {
        return try {
            val request = recipesApi.getRandomRecipes(filters)
            Result.Success(
                data = request
            )
        } catch (e: Exception) {
            Result.Error(
                message = "Error while fetching recipes"
            )
        }
    }
}