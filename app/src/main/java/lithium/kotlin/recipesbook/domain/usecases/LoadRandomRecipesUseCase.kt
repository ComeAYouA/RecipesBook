package lithium.kotlin.recipesbook.domain.usecases

import lithium.kotlin.recipesbook.domain.models.RecipeResource
import lithium.kotlin.recipesbook.domain.models.Result
import lithium.kotlin.recipesbook.domain.rescipes_api.RecipesApi
import lithium.kotlin.recipesbook.recipe_screen.RecipeScreenUiState
import javax.inject.Inject

class LoadRandomRecipesUseCase @Inject constructor(
    private val recipesApi: RecipesApi
) {
    suspend operator fun invoke(): Result<List<RecipeResource>>{
        return try {
            val request = recipesApi.getRandomRecipes().recipes
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