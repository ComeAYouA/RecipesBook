package lithium.kotlin.recipesbook.feature.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lithium.kotlin.recipesbook.core.domain.GetRecipeInformationUseCase
import lithium.kotlin.recipesbook.core.domain.RecipesBaseUseCase
import lithium.kotlin.recipesbook.core.model.RecipeInformation
import lithium.kotlin.recipesbook.core.model.RecipePreview
import lithium.kotlin.recipesbook.core.model.Result
import lithium.kotlin.recipesbook.feature.recipe.navigation.RecipeArgs
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipesBaseUseCase: GetRecipeInformationUseCase
): ViewModel() {

    private val recipeArgs: RecipeArgs = RecipeArgs(savedStateHandle)

    val recipeId = recipeArgs.recipeId.toLong()

    internal val screenUiState: MutableStateFlow<RecipeUIState> =
        MutableStateFlow(
            RecipeUIState.Loading
        )

    init {
        screenUiState.update { RecipeUIState.Loading }

        viewModelScope.launch {
            val requestResult = recipesBaseUseCase(recipeId)

            updateContentUiStateWithResult(requestResult)
        }
    }

    private fun updateContentUiStateWithResult(result: Result<RecipeInformation>) =
        screenUiState.update {
            when (result) {
                is Result.Success -> {
                    RecipeUIState.Success(
                        data = result.data
                    )
                }

                is Result.Error -> {
                    RecipeUIState.Error(
                        result.message
                    )
                }
            }
        }

}