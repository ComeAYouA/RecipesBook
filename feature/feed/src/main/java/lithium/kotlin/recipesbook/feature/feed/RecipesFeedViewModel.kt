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
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject

@HiltViewModel
class RecipesFeedViewModel @Inject constructor(
    private val recipesBaseUseCase: RecipesBaseUseCase
): ViewModel() {

//    internal val contentUiState: MutableStateFlow<RecipesFeedContentUiState> =
//        MutableStateFlow(RecipesFeedContentUiState.Loading)

    internal val screenUiState: MutableStateFlow<RecipesFeedUiState> =
        MutableStateFlow(
            RecipesFeedUiState.Loading
        )

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

    fun searchRandomRecipes(query: String) {

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

            val requestResult = recipesBaseUseCase.searchRecipesUseCase(query = query)

            updateContentUiStateWithResult(requestResult)
        }
    }

    fun searchBookmarkedRecipes(query: String){
        screenUiState.update {
            RecipesFeedUiState.Loading
        }

        requestsContext.cancelChildren()

        query.ifBlank {
            loadBookmarks()
            return
        }


        viewModelScope.launch {

            viewModelScope.launch(requestsContext) {
                delay(1000)

                val requestResult = recipesBaseUseCase.searchRecipesInBookmarksUseCase(query = query)

                updateContentUiStateWithResult(requestResult)
            }
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

    fun loadBookmarks() {

        viewModelScope.launch {

            screenUiState.update {
                RecipesFeedUiState.Loading
            }

            updateContentUiStateWithResult(
                result =  recipesBaseUseCase.loadBookmarksUseCase()
            )
        }
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