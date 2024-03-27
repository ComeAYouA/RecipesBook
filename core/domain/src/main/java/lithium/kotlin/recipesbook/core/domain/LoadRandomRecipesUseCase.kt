package lithium.kotlin.recipesbook.core.domain

import kotlinx.coroutines.cancel
import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Filter
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

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
                message = "Error while fetching recipes"
            )
        }
    }
}