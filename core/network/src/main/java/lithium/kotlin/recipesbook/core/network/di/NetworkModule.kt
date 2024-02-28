package lithium.kotlin.recipesbook.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import lithium.kotlin.recipesbook.core.network.RecipesApi
import lithium.kotlin.recipesbook.core.network.retrofit.RecipesApiImpl
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule{

    @Binds
    fun bindRecipeApiImpl_to_RecipeApi(input: RecipesApiImpl): RecipesApi

    companion object{
        @OptIn(ExperimentalSerializationApi::class)
        private val json = Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }

        @Provides
        fun providesRetrofit(): Retrofit{
            return Retrofit
                .Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
        }
    }
}