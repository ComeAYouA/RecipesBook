package lithium.kotlin.recipesbook.core.domain

import android.util.Log
import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.RecipeInformation
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject

class GetRecipeInformationUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
){
    suspend operator fun invoke(id: Long): Result<RecipeInformation>{
        return try {
            val request = recipesRepository.getRecipeInformation(id)
            Log.d("myTag", request.dishTypes.toString())
            Result.Success(
                data = request
            )

        } catch (e: Exception) {
            Log.d("myTag", e.message.toString())
            Result.Error(
                message = e.message?: "Unknown Error"
            )
        }
    }
}