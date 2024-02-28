package lithium.kotlin.recipesbook.core.data

import lithium.kotlin.recipesbook.core.model.Recipe

interface RecipesRepository {

    suspend fun getRandomRecipes(): List<Recipe>

    suspend fun searchRecipes(query: String): List<Recipe>


}