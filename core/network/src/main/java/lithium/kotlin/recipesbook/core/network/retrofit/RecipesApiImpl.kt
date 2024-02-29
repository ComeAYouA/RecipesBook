package lithium.kotlin.recipesbook.core.network.retrofit

import lithium.kotlin.recipesbook.core.network.RecipesApi
import lithium.kotlin.recipesbook.core.network.model.NetworkRecipeResource
import lithium.kotlin.recipesbook.core.network.model.RandomRecipesResponse
import lithium.kotlin.recipesbook.core.network.model.SearchRecipesResponse
import retrofit2.Retrofit
import javax.inject.Inject

internal class RecipesApiImpl @Inject constructor(
    private val retrofit: Retrofit
): RecipesApi {

    private val recipeApi: RecipesApi = retrofit.create(RecipesApi::class.java)
    override suspend fun getRandomRecipes(number: Int): RandomRecipesResponse =
        recipeApi.getRandomRecipes()
    override suspend fun searchRecipes(query: String, recipeInformation: Boolean): SearchRecipesResponse =
        recipeApi. searchRecipes(query)

    override suspend fun getRecipesByIds(ids: List<String>): List<NetworkRecipeResource> =
        recipeApi.getRecipesByIds(ids)

}