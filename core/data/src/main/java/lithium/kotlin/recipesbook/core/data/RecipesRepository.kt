package lithium.kotlin.recipesbook.core.data

import lithium.kotlin.recipesbook.core.model.Recipe

interface RecipesRepository {

    suspend fun getRandomRecipes(): List<Recipe>
    suspend fun searchRecipes(query: String): List<Recipe>
    suspend fun getBookmarkedRecipes(): List<Recipe>
    suspend fun deleteBookmarkedRecipe(recipe: Recipe)
    suspend fun addBookmarkedRecipe(recipe: Recipe)
}