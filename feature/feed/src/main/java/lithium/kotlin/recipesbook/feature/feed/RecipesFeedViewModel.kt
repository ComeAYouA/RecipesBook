package lithium.kotlin.recipesbook.feature.feed

import android.util.Log
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
import lithium.kotlin.recipesbook.core.domain.AddBookmarkedRecipeUseCase
import lithium.kotlin.recipesbook.core.domain.DeleteBookmarkedRecipeUseCase
import lithium.kotlin.recipesbook.core.domain.LoadBookmarkedRecipes
import lithium.kotlin.recipesbook.core.domain.LoadRandomRecipesUseCase
import lithium.kotlin.recipesbook.core.domain.SearchRecipesUseCase
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject

@HiltViewModel
class RecipesFeedViewModel @Inject constructor(
    private val loadRandomRecipesUseCase: LoadRandomRecipesUseCase,
    private val searchRecipesUseCase: SearchRecipesUseCase,
    private val loadBookmarkedRecipes: LoadBookmarkedRecipes,
    private val addBookmarkedRecipeUseCase: AddBookmarkedRecipeUseCase,
    private val deleteBookmarkedRecipeUseCase: DeleteBookmarkedRecipeUseCase
): ViewModel() {

    internal val contentUiState: MutableStateFlow<RecipesFeedContentUiState> =
        MutableStateFlow(RecipesFeedContentUiState.Loading)

    internal val screenUiState: MutableStateFlow<RecipeScreenUiState> =
        MutableStateFlow(RecipeScreenUiState(content = Content.InterestingRecipes))

    private val requestsContext = SupervisorJob() + Dispatchers.IO

    init {
        loadRandomRecipes()
    }

    fun loadRandomRecipes() {
        viewModelScope.launch {

            contentUiState.update {
                RecipesFeedContentUiState.Loading
            }

            val requestResult = loadRandomRecipesUseCase()

            contentUiState.update {
                when (requestResult) {
                    is Result.Success -> {
                        RecipesFeedContentUiState.Success(
                            data = requestResult.data
                        )
                    }

                    is Result.Error -> {
                        RecipesFeedContentUiState.Error(
                            requestResult.message
                        )
                    }
                }
            }
        }
    }

    fun searchRecipes(query: String) {

        contentUiState.update {
            RecipesFeedContentUiState.Loading
        }

        requestsContext.cancelChildren()

        if (query == "") {
            loadRandomRecipes()
            return
        }

        viewModelScope.launch(requestsContext) {
            delay(1000)

            val requestResult = searchRecipesUseCase(query = query)

            contentUiState.update {
                when (requestResult) {
                    is Result.Success -> {
                        RecipesFeedContentUiState.Success(
                            data = requestResult.data
                        )
                    }

                    is Result.Error -> {
                        RecipesFeedContentUiState.Error(
                            requestResult.message
                        )
                    }
                }
            }
        }
    }

    fun bookmarkRecipe(recipeId: Long) {
        viewModelScope.launch {
            addBookmarkedRecipeUseCase(recipeId)
        }
    }

    fun unbookmarkRecipe(recipeId: Long) {
        viewModelScope.launch {
            deleteBookmarkedRecipeUseCase(recipeId)
        }
    }

    fun loadBookmarks() {
        viewModelScope.launch {
            screenUiState.update {
                RecipeScreenUiState(
                    content = Content.FavoriteRecipes
                )
            }

            val requestResult = loadBookmarkedRecipes()

            contentUiState.update {
                when (requestResult) {
                    is Result.Success -> {
                        RecipesFeedContentUiState.Success(
                            data = requestResult.data
                        )
                    }

                    is Result.Error -> {
                        RecipesFeedContentUiState.Error(
                            requestResult.message
                        )
                    }
                }
            }
        }
    }
}