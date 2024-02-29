package lithium.kotlin.recipesbook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lithium.kotlin.recipesbook.database.model.RecipeEntity


@Dao
interface RecipesDao {

    @Query("SELECT * FROM recipeentity")
    suspend fun getBookmarkedRecipes(): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmarkedRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM recipeentity WHERE id = :recipeId")
    suspend fun deleteBookmarkedRecipe(recipeId: Long)
}