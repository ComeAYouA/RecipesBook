package lithium.kotlin.recipesbook.domain.usecases

import lithium.kotlin.recipesbook.domain.models.RecipeResource
import lithium.kotlin.recipesbook.domain.rescipes_api.RecipesApi
import lithium.kotlin.recipesbook.domain.models.Result
import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val recipesApi: RecipesApi
) {
    suspend operator fun invoke(query: String): Result<List<RecipeResource>>{
        return try {
            val request = recipesApi.searchRecipes(query).result
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