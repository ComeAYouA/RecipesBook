package lithium.kotlin.recipesbook.core.domain

import android.util.Log
import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Recipe
import javax.inject.Inject

class AddBookmarkedRecipeUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(recipe: Recipe) {
        try {
            recipesRepository.addBookmarkedRecipe(recipe)
        } catch (e: Exception) {
            Log.d("myTag", e.message.toString())
        }
    }
}