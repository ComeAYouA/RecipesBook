package lithium.kotlin.recipesbook.core.data.repository

import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.network.RecipesApi
import lithium.kotlin.recipesbook.core.network.model.NetworkRecipeResource
import lithium.kotlin.recipesbook.core.network.model.asExternalEntity
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val network: RecipesApi
): RecipesRepository {
    override suspend fun getRandomRecipes(): List<Recipe> =
        network.getRandomRecipes().recipes.map(NetworkRecipeResource::asExternalEntity)

    override suspend fun searchRecipes(query: String): List<Recipe> =
        network.searchRecipes(query).recipes.map(NetworkRecipeResource::asExternalEntity)
}