package lithium.kotlin.recipesbook.core.data.repository

import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Filter
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.network.RecipesApi
import lithium.kotlin.recipesbook.core.network.model.NetworkRecipeResource
import lithium.kotlin.recipesbook.core.network.model.asExternalModel
import lithium.kotlin.recipesbook.core.network.model.cuisineFilter
import lithium.kotlin.recipesbook.core.network.model.dietFilter
import lithium.kotlin.recipesbook.core.network.model.toNetworkQuery
import lithium.kotlin.recipesbook.database.RecipesDao
import lithium.kotlin.recipesbook.database.model.RecipeEntity
import javax.inject.Inject

internal class RecipesRepositoryImpl @Inject constructor(
    private val network: RecipesApi,
    private val recipesDao: RecipesDao
): RecipesRepository {
    override suspend fun getRandomRecipes(filters: List<Filter>): List<Recipe> {
        val cuisine = filters.cuisineFilter?.toNetworkQuery()?.lowercase()
        val diet = filters.dietFilter?.toNetworkQuery()?.lowercase()

        val filterQuery: String? = when{
            cuisine.isNullOrEmpty() && diet.isNullOrEmpty()-> null
            diet.isNullOrEmpty() -> cuisine
            cuisine.isNullOrEmpty() -> diet
            else -> "$cuisine, $diet"
        }

        return network.getRandomRecipes(filters = filterQuery).recipes.map(NetworkRecipeResource::asExternalModel)
    }
    override suspend fun searchRecipes(query: String, filters: List<Filter>): List<Recipe> {
        val cuisine = filters.cuisineFilter?.toNetworkQuery()
        val diet = filters.dietFilter?.toNetworkQuery()

        return network.searchRecipes(
            query,
            diet = diet,
            cuisine = cuisine
        ).recipes
            .map(NetworkRecipeResource::asExternalModel)
    }
    override suspend fun getBookmarkedRecipes(): List<Recipe> {
        val userData = recipesDao.getBookmarkedRecipes().map { it.id }.joinToString(separator = ",")

        return network.getRecipesByIds(userData).map(NetworkRecipeResource::asBookmarkedRecipeModel)
    }
    override suspend fun deleteBookmarkedRecipe(recipe: Recipe) = recipesDao.deleteBookmarkedRecipe(recipe.id)
    override suspend fun addBookmarkedRecipe(recipe: Recipe)
        = recipesDao.addBookmarkedRecipe(
            RecipeEntity(
                id = recipe.id,
                isBookMarked = true,
                tittle = recipe.tittle ?: ""
            )
        )
}

fun NetworkRecipeResource.asBookmarkedRecipeModel(): Recipe =
    this.asExternalModel().copy(isBookmarked = true)