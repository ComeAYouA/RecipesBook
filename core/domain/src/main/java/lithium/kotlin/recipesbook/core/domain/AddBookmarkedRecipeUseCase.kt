package lithium.kotlin.recipesbook.core.domain

import android.util.Log
import lithium.kotlin.recipesbook.core.data.RecipesRepository
import javax.inject.Inject

class AddBookmarkedRecipeUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(recipesId: Long) {
        try {
            recipesRepository.addBookmarkedRecipe(recipesId)
        } catch (e: Exception) {
            Log.d("myTag", e.message.toString())
        }
    }
}