package lithium.kotlin.recipesbook.core.data.repository

import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Filter
import lithium.kotlin.recipesbook.core.model.RecipeInformation
import lithium.kotlin.recipesbook.core.model.RecipePreview
import lithium.kotlin.recipesbook.core.network.RecipesApi
import lithium.kotlin.recipesbook.core.network.model.NetworkRecipePreviewResource
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
    override suspend fun getRandomRecipes(filters: List<Filter>): List<RecipePreview> {
        val cuisine = filters.cuisineFilter?.toNetworkQuery()?.lowercase()
        val diet = filters.dietFilter?.toNetworkQuery()?.lowercase()

        val filterQuery: String? = when{
            cuisine.isNullOrEmpty() && diet.isNullOrEmpty()-> null
            diet.isNullOrEmpty() -> cuisine
            cuisine.isNullOrEmpty() -> diet
            else -> "$cuisine, $diet"
        }

        return network
            .getRandomRecipes(filters = filterQuery)
            .recipes
            .map(NetworkRecipePreviewResource::asExternalModel)
    }
    override suspend fun searchRecipes(query: String, filters: List<Filter>): List<RecipePreview> {
        val cuisine = filters.cuisineFilter?.toNetworkQuery()?.lowercase()
        val diet = filters.dietFilter?.toNetworkQuery()?.lowercase()

        return network.searchRecipes(
            query,
            diet = diet,
            cuisine = cuisine
        ).recipes
            .map(NetworkRecipePreviewResource::asExternalModel)
    }

    override suspend fun getRecipeInformation(id: Long): RecipeInformation {
        val recipeInformation = network.getRecipeInformation(id)
        return recipeInformation.asExternalModel()
    }

    //* Bookmark recipes methods rework require in future *//

    // It is necessary to save information about selected recipes in a local database
    // and then update the information with the api

    override suspend fun getBookmarkedRecipes(): List<RecipePreview> {
        val userData = recipesDao
            .getBookmarkedRecipes()
            .map { it.id }
            .joinToString(separator = ",")

        return network
            .getRecipesByIds(userData)
            .map(NetworkRecipePreviewResource::asBookmarkedRecipeModel)
    }
    override suspend fun deleteBookmarkedRecipe(recipePreview: RecipePreview) =
        recipesDao.deleteBookmarkedRecipe(recipePreview.id)
    override suspend fun addBookmarkedRecipe(recipePreview: RecipePreview)
        = recipesDao.addBookmarkedRecipe(
            RecipeEntity(
                id = recipePreview.id,
                isBookMarked = true,
                tittle = recipePreview.tittle ?: ""
            )
        )
}

fun NetworkRecipePreviewResource.asBookmarkedRecipeModel(): RecipePreview =
    this.asExternalModel().copy(isBookmarked = true)