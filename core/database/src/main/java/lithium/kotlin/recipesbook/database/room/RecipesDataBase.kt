package lithium.kotlin.recipesbook.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import lithium.kotlin.recipesbook.database.RecipesDao
import lithium.kotlin.recipesbook.database.model.RecipeEntity


@Database(entities = [RecipeEntity::class], version = 1)
abstract class RecipesDataBase: RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}