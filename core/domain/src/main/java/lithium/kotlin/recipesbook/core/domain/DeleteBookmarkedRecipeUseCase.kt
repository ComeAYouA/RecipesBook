package lithium.kotlin.recipesbook.core.domain

import android.util.Log
import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject

class DeleteBookmarkedRecipeUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(recipesId: Long) {
        try {
            recipesRepository.deleteBookmarkedRecipe(recipesId)
        } catch (e: Exception) {
            Log.d("myTag", e.message.toString())
        }
    }
}