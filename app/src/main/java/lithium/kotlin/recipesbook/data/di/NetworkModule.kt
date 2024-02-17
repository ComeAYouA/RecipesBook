package lithium.kotlin.recipesbook.data.di


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lithium.kotlin.recipesbook.data.recipeApi.RecipeApiImpl
import lithium.kotlin.recipesbook.domain.rescipes_api.RecipesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun bindRecipeApiImpl_to_RecipeApi(input: RecipeApiImpl): RecipesApi

    companion object{
        @Provides
        fun provideRetrofit(): Retrofit {
            return Retrofit
                .Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}