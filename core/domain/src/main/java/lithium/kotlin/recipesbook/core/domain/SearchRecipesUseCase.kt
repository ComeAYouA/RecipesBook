package lithium.kotlin.recipesbook.core.domain

import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Filter
import lithium.kotlin.recipesbook.core.model.RecipePreview
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(
        query: String,
        filters: List<Filter>
    ): Result<List<RecipePreview>> {
        return try {
            val request = repository.searchRecipes(query, filters)

            Result.Success(
                data = request
            )
        } catch (e: Exception) {
            Result.Error(
                message = "Error while searching recipes"
            )
        }
    }
}