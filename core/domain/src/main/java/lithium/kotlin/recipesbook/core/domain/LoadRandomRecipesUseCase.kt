package lithium.kotlin.recipesbook.core.domain

import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject
class LoadRandomRecipesUseCase @Inject constructor(
    private val recipesApi: RecipesRepository
) {
    suspend operator fun invoke(): Result<List<Recipe>> {
        return try {
            val request = recipesApi.getRandomRecipes()
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