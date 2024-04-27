package lithium.kotlin.recipesbook.core.data

import lithium.kotlin.recipesbook.core.model.Filter
import lithium.kotlin.recipesbook.core.model.RecipeInformation
import lithium.kotlin.recipesbook.core.model.RecipePreview

interface RecipesRepository {

    suspend fun getRandomRecipes(filters: List<Filter>): List<RecipePreview>
    suspend fun searchRecipes(query: String, filters: List<Filter>): List<RecipePreview>
    suspend fun getRecipeInformation(id: Long): RecipeInformation
    suspend fun getBookmarkedRecipes(): List<RecipePreview>
    suspend fun deleteBookmarkedRecipe(recipePreview: RecipePreview)
    suspend fun addBookmarkedRecipe(recipePreview: RecipePreview)
}