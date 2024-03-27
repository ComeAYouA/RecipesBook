package lithium.kotlin.recipesbook.feature.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lithium.kotlin.recipesbook.core.domain.RecipesBaseUseCase
import lithium.kotlin.recipesbook.core.model.CuisineFilter
import lithium.kotlin.recipesbook.core.model.DietFilter
import lithium.kotlin.recipesbook.core.model.Filter
import lithium.kotlin.recipesbook.core.model.FilterProperty
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject

@HiltViewModel
class RecipesFeedViewModel @Inject constructor(
    private val recipesBaseUseCase: RecipesBaseUseCase
): ViewModel() {
    internal val screenUiState: MutableStateFlow<RecipesFeedUiState> =
        MutableStateFlow(
            RecipesFeedUiState.Loading
        )

    internal val recipesFeedFilters: List<Filter> = listOf(DietFilter(), CuisineFilter())

    private val requestsContext = SupervisorJob() + Dispatchers.IO

    init {
        loadRandomRecipes()
    }

    fun loadRandomRecipes() {
        viewModelScope.launch {

            screenUiState.update {
                RecipesFeedUiState.Loading
            }


            updateContentUiStateWithResult(
                result = recipesBaseUseCase.loadRandomRecipesUseCase()
            )
        }
    }

    fun searchRecipes(query: String) {

        screenUiState.update {
            RecipesFeedUiState.Loading
        }

        requestsContext.cancelChildren()

        query.ifBlank {
            loadRandomRecipes()
            return
        }

        viewModelScope.launch(requestsContext) {
            delay(1000)

            val requestResult = recipesBaseUseCase.searchRecipesUseCase(
                query = query,
                filters = recipesFeedFilters.toList()
            )

            updateContentUiStateWithResult(requestResult)
        }
    }
    fun bookmarkRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipesBaseUseCase.addBookmarkedRecipeUseCase(recipe)
        }
    }

    fun unbookmarkRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipesBaseUseCase.deleteBookmarkedRecipeUseCase(recipe)
        }
    }

    fun changeFilter(filter: Filter, property: FilterProperty, isSelected: Boolean){
        val filterIndex = recipesFeedFilters.indexOf(filter)
        val propertyIndex = recipesFeedFilters[filterIndex].properties.indexOf(property)
        recipesFeedFilters[filterIndex].properties[propertyIndex].isSelected = isSelected
    }

    private fun updateContentUiStateWithResult(result: Result<List<Recipe>>) =
        screenUiState.update {
            when (result) {
                    is Result.Success -> {
                        RecipesFeedUiState.Success(
                            data = result.data
                        )
                    }

                    is Result.Error -> {
                        RecipesFeedUiState.Error(
                            result.message
                        )
                    }
                }
        }
}