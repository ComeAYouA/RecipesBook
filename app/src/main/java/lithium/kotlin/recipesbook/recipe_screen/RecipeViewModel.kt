package lithium.kotlin.recipesbook.recipe_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lithium.kotlin.recipesbook.domain.rescipes_api.RecipesApi
import lithium.kotlin.recipesbook.domain.models.RecipeResource
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipesApi: RecipesApi
): ViewModel() {

    val uiState: MutableStateFlow<RecipeScreenUiState> = MutableStateFlow(RecipeScreenUiState.Loading)

    fun loadRandomRecipes(){
        viewModelScope.launch {
            val request = recipesApi.getRandomRecipes().recipes
            uiState.update {
                RecipeScreenUiState.Success(
                    request
                )
            }
            Log.d("myTag", request.toString())
        }
    }
}

sealed interface RecipeScreenUiState {
    object Loading : RecipeScreenUiState
    data class Success(
        val data: List<RecipeResource>
    ) : RecipeScreenUiState

    data class Error(
        val message: String
    ) : RecipeScreenUiState
}