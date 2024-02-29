package lithium.kotlin.recipesbook.core.data.repository

import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.network.RecipesApi
import lithium.kotlin.recipesbook.core.network.model.NetworkRecipeResource
import lithium.kotlin.recipesbook.core.network.model.asExternalModel
import lithium.kotlin.recipesbook.database.RecipesDao
import lithium.kotlin.recipesbook.database.model.RecipeEntity
import javax.inject.Inject

internal class RecipesRepositoryImpl @Inject constructor(
    private val network: RecipesApi,
    private val recipesDao: RecipesDao
): RecipesRepository {
    override suspend fun getRandomRecipes(): List<Recipe> =
        network.getRandomRecipes().recipes.map(NetworkRecipeResource::asExternalModel)

    override suspend fun searchRecipes(query: String): List<Recipe> =
        network.searchRecipes(query).recipes
            .map(NetworkRecipeResource::asExternalModel)

    override suspend fun getBookmarkedRecipes(): List<Recipe> {
        val userData = recipesDao.getBookmarkedRecipes().map { it.id.toString() }

        return network.getRecipesByIds(userData).map(NetworkRecipeResource::asBookmarkedRecipeModel)
    }

    override suspend fun deleteBookmarkedRecipe(recipeId: Long) = recipesDao.deleteBookmarkedRecipe(recipeId)
    override suspend fun addBookmarkedRecipe(recipesId: Long)
        = recipesDao.addBookmarkedRecipe(
            RecipeEntity(
                id = recipesId,
                isBookMarked = true
            )
        )
}

fun NetworkRecipeResource.asBookmarkedRecipeModel(): Recipe =
    this.asExternalModel().copy(isBookmarked = true)