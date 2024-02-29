package lithium.kotlin.recipesbook.core.domain

import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject

class LoadBookmarkedRecipes @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(): Result<List<Recipe>> {
        return try {
            val request = recipesRepository.getBookmarkedRecipes()
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