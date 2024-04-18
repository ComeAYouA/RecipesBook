package lithium.kotlin.recipesbook.core.network

import androidx.core.os.BuildCompat
import lithium.kotlin.recipesbook.core.network.model.NetworkRecipeResource
import lithium.kotlin.recipesbook.core.network.model.RandomRecipesResponse
import lithium.kotlin.recipesbook.core.network.model.SearchRecipesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface RecipesApi {

    companion object {
        private const val API_KEY = BuildConfig.apiKey
    }

    @Headers("x-api-key: $API_KEY")
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int = 50,
        @Query("include-tags") filters: String? = null
    ): RandomRecipesResponse


    // format of cuisine and diet filters parameter: filter1, filter2, filter3 (A comma-separated list of filters.)
    @Headers("x-api-key: $API_KEY")
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("cuisine") cuisine: String? = null,
        @Query("diet") diet: String? = null,
        @Query("addRecipeInformation") recipeInformation: Boolean = true
    ): SearchRecipesResponse



    // format of ids parameter: id1, id2, id3 (A comma-separated list of recipe ids.)
    @Headers("x-api-key: $API_KEY")
    @GET("recipes/informationBulk")
    suspend fun getRecipesByIds(
        @Query("ids") ids: String,
    ): List<NetworkRecipeResource>


}