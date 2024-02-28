package lithium.kotlin.recipesbook.core.domain

import lithium.kotlin.recipesbook.core.data.RecipesRepository
import javax.inject.Inject
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.Result

class SearchRecipesUseCase @Inject constructor(
    private val recipesApi: RecipesRepository
) {
    suspend operator fun invoke(query: String): Result<List<Recipe>> {
        return try {
            val request = recipesApi.searchRecipes(query)
            Result.Success(
                data = request
            )
        } catch (e: Exception) {
            Result.Error(
                message = e.message?: "Unknown Error"
            )
        }
    }
}