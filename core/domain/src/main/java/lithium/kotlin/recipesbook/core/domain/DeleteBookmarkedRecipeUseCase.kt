package lithium.kotlin.recipesbook.core.domain

import android.util.Log
import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.RecipePreview
import javax.inject.Inject

class DeleteBookmarkedRecipeUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(recipePreview: RecipePreview) {
        try {
            recipesRepository.deleteBookmarkedRecipe(recipePreview)
        } catch (e: Exception) {
            Log.d("myTag", e.message.toString())
        }
    }
}