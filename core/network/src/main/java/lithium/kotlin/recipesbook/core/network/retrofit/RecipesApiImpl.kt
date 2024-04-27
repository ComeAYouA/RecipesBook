package lithium.kotlin.recipesbook.core.network.retrofit

import lithium.kotlin.recipesbook.core.network.RecipesApi
import lithium.kotlin.recipesbook.core.network.model.NetworkRecipePreviewResource
import lithium.kotlin.recipesbook.core.network.model.NetworkRecipeResource
import lithium.kotlin.recipesbook.core.network.model.RandomRecipesResponse
import lithium.kotlin.recipesbook.core.network.model.SearchRecipesResponse
import retrofit2.Retrofit
import javax.inject.Inject

internal class RecipesApiImpl @Inject constructor(
    private val retrofit: Retrofit
): RecipesApi {

    private val recipeApi: RecipesApi = retrofit.create(RecipesApi::class.java)
    override suspend fun getRandomRecipes(
       number: Int,
       filters: String?
    ): RandomRecipesResponse =
        recipeApi.getRandomRecipes(filters = filters)

    override suspend fun searchRecipes(
        query: String,
        cuisine: String?,
        diet: String?,
        recipeInformation: Boolean
    ): SearchRecipesResponse =
        recipeApi. searchRecipes(query, cuisine, diet)


    override suspend fun getRecipesByIds(ids: String): List<NetworkRecipePreviewResource> =
        recipeApi.getRecipesByIds(ids)

    override suspend fun getRecipeInformation(id: Long): NetworkRecipeResource =
        recipeApi.getRecipeInformation(id)

}