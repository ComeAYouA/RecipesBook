package lithium.kotlin.recipesbook.recipe_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lithium.kotlin.recipesbook.domain.rescipes_api.RecipesApi
import lithium.kotlin.recipesbook.domain.models.RecipeResource
import lithium.kotlin.recipesbook.domain.models.Result
import lithium.kotlin.recipesbook.domain.usecases.LoadRandomRecipesUseCase
import lithium.kotlin.recipesbook.domain.usecases.SearchRecipesUseCase
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
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
        val data: List<RecipeResource>
    ) : RecipeScreenUiState
    data class Error(
        val message: String
    ) : RecipeScreenUiState
}