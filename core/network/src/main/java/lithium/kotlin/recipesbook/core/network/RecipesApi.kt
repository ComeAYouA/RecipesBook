package lithium.kotlin.recipesbook.core.network

import lithium.kotlin.recipesbook.core.network.model.NetworkRecipeResource
import lithium.kotlin.recipesbook.core.network.model.RandomRecipesResponse
import lithium.kotlin.recipesbook.core.network.model.SearchRecipesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val API_KEY = "e1ef555cb2154ed0bd1a290ab8a32e3e"

interface RecipesApi {

    @Headers("x-api-key: $API_KEY")
    @GET("recipes/random")
    suspend fun getRandomRecipes(@Query("number") number: Int = 50): RandomRecipesResponse

    @Headers("x-api-key: $API_KEY")
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("addRecipeInformation") recipeInformation: Boolean = true
    ): SearchRecipesResponse



    // format of ids parameter: id1, id2, id3 (A comma-separated list of recipe ids.)
    @Headers("x-api-key: $API_KEY")
    @GET("recipes/informationBulk")
    suspend fun getRecipesByIds(
        @Query("ids") ids: String,
    ): List<NetworkRecipeResource>
}