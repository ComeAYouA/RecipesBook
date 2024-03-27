package lithium.kotlin.recipesbook.core.data

import lithium.kotlin.recipesbook.core.model.Filter
import lithium.kotlin.recipesbook.core.model.FilterProperty
import lithium.kotlin.recipesbook.core.model.Recipe

interface RecipesRepository {

    suspend fun getRandomRecipes(): List<Recipe>
    suspend fun searchRecipes(query: String, filters: List<Filter>): List<Recipe>
    suspend fun getBookmarkedRecipes(): List<Recipe>
    suspend fun deleteBookmarkedRecipe(recipe: Recipe)
    suspend fun addBookmarkedRecipe(recipe: Recipe)
}