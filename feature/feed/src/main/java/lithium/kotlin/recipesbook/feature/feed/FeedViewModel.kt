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
import lithium.kotlin.recipesbook.core.model.RecipePreview
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject

@HiltViewModel
internal class FeedViewModel @Inject constructor(
    private val recipesBaseUseCase: RecipesBaseUseCase
): ViewModel() {
    internal val screenUiState: MutableStateFlow<FeedUiState> =
        MutableStateFlow(
            FeedUiState.Loading
        )

    internal val feedFilters: List<Filter> = listOf(DietFilter(), CuisineFilter())
    internal val feedFiltersIsEmpty: Boolean
        get() = feedFilters.find { filter ->
            filter.properties.find { it.isSelected } != null
        } == null

    private val requestsContext = SupervisorJob() + Dispatchers.IO

    init {
        loadRandomRecipes()
    }

    private fun loadRandomRecipes() {
        viewModelScope.launch {
            screenUiState.update {
                FeedUiState.Loading
            }

            updateContentUiStateWithResult(
                result = recipesBaseUseCase.loadRandomRecipesUseCase(feedFilters.toList())
            )
        }
    }

    fun searchRecipes(query: String) {

        screenUiState.update {
            FeedUiState.Loading
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
                filters = feedFilters.toList()
            )

            updateContentUiStateWithResult(requestResult)
        }
    }
    fun bookmarkRecipe(recipePreview: RecipePreview) {
        viewModelScope.launch {
            recipesBaseUseCase.addBookmarkedRecipeUseCase(recipePreview)
        }
    }

    fun unbookmarkRecipe(recipePreview: RecipePreview) {
        viewModelScope.launch {
            recipesBaseUseCase.deleteBookmarkedRecipeUseCase(recipePreview)
        }
    }

    fun changeFilter(filter: Filter, property: FilterProperty, isSelected: Boolean){
        val filterIndex = feedFilters.indexOf(filter)
        val propertyIndex = feedFilters[filterIndex].properties.indexOf(property)
        feedFilters[filterIndex].properties[propertyIndex].isSelected = isSelected
    }

    private fun updateContentUiStateWithResult(result: Result<List<RecipePreview>>) =
        screenUiState.update {
            when (result) {
                    is Result.Success -> {
                        FeedUiState.Success(
                            data = result.data
                        )
                    }

                    is Result.Error -> {
                        FeedUiState.Error(
                            result.message
                        )
                    }
                }
        }
}