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
import lithium.kotlin.recipesbook.core.domain.LoadRandomRecipesUseCase
import lithium.kotlin.recipesbook.core.domain.SearchRecipesUseCase
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.Result
import javax.inject.Inject

@HiltViewModel
class RecipesFeedViewModel @Inject constructor(
    private val loadRandomRecipesUseCase: LoadRandomRecipesUseCase,
    private val searchRecipesUseCase: SearchRecipesUseCase
): ViewModel() {

    val uiState: MutableStateFlow<RecipeScreenUiState> =
        MutableStateFlow(RecipeScreenUiState.Loading)
    private val requestsContext = SupervisorJob() + Dispatchers.IO

    init {
        loadRandomRecipes()
    }

    fun loadRandomRecipes() {
        viewModelScope.launch {

            uiState.update {
                RecipeScreenUiState.Loading
            }

            val requestResult = loadRandomRecipesUseCase()

            uiState.update {
                when(requestResult){
                    is Result.Success -> {
                        RecipeScreenUiState.Success(
                            requestResult.data
                        )
                    }
                    is Result.Error -> {
                        RecipeScreenUiState.Error(
                            requestResult.message
                        )
                    }
                }
            }
        }
    }
    fun searchRecipes(query: String) {

        uiState.update {
            RecipeScreenUiState.Loading
        }

        if (query == ""){
            loadRandomRecipes()
            return
        }

        requestsContext.cancelChildren()

        viewModelScope.launch(requestsContext) {
            delay(1000)

            val requestResult = searchRecipesUseCase(query =  query)

            uiState.update {
                when(requestResult){
                    is Result.Success -> {
                        RecipeScreenUiState.Success(
                            requestResult.data
                        )
                    }
                    is Result.Error -> {
                        RecipeScreenUiState.Error(
                            requestResult.message
                        )
                    }
                }
            }
        }
    }
}



sealed interface RecipeScreenUiState {
    object Loading : RecipeScreenUiState
    data class Success(
        val data: List<Recipe>
    ) : RecipeScreenUiState
    data class Error(
        val message: String
    ) : RecipeScreenUiState
}