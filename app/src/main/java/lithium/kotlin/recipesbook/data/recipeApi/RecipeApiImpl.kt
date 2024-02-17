package lithium.kotlin.recipesbook.data.recipeApi

import lithium.kotlin.recipesbook.domain.rescipes_api.RecipesApi
import lithium.kotlin.recipesbook.domain.models.RandomRecipesResponse
import retrofit2.Retrofit
import javax.inject.Inject



class RecipeApiImpl @Inject constructor(
    private val retrofit: Retrofit
): RecipesApi {

    private val recipeApi: RecipesApi = retrofit.create(RecipesApi::class.java)
    override suspend fun getRandomRecipes(number: Int): RandomRecipesResponse = recipeApi.getRandomRecipes()

}