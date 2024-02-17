package lithium.kotlin.recipesbook.domain.rescipes_api

import lithium.kotlin.recipesbook.domain.models.RandomRecipesResponse
import retrofit2.http.GET

import retrofit2.http.Headers
import retrofit2.http.Query

private const val API_KEY = "e1ef555cb2154ed0bd1a290ab8a32e3e"

interface RecipesApi {

    @Headers("x-api-key: $API_KEY")
    @GET("recipes/random")
    suspend fun getRandomRecipes(@Query("number") number: Int = 50): RandomRecipesResponse
}