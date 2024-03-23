package lithium.kotlin.recipesbook.core.domain

import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject

class SearchRecipesInBookmarksUseCase @Inject constructor(
    private val repository: RecipesRepository
) {

    suspend operator fun invoke(query: String): Result<List<Recipe>>{
        return try {
            val request = repository.getBookmarkedRecipes()
            val data = request.fold(mutableListOf<Recipe>()){ acc, recipe ->
                recipe.tittle?.let { if (it.contains(query, ignoreCase = true)) acc.add(recipe)}
                acc
            }
            Result.Success(
                data = data
            )
        } catch (e: Exception) {
            Result.Error(
                message = e.message?: "Unknown Error"
            )
        }
    }
}