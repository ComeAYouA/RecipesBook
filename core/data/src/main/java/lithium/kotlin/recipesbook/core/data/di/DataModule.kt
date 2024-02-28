package lithium.kotlin.recipesbook.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lithium.kotlin.recipesbook.core.data.RecipesRepository
import lithium.kotlin.recipesbook.core.data.repository.RecipesRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bind_from_RecipesRepositoryImpl_to_RecipesRepository(input: RecipesRepositoryImpl): RecipesRepository

}