package lithium.kotlin.recipesbook.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lithium.kotlin.recipesbook.database.RecipesDao
import lithium.kotlin.recipesbook.database.room.RecipesDataBase

@Module
@InstallIn(SingletonComponent::class)
interface DataBaseModule {

    companion object{

        @Provides
        fun provideRecipesDataBase(
            @ApplicationContext context: Context
        ): RecipesDataBase =
            Room.databaseBuilder(
                context,
                RecipesDataBase::class.java,
                "recipes-database"
            ).build()

        @Provides
        fun provideRecipesDao(database: RecipesDataBase): RecipesDao = database.recipesDao()
    }
}