package lithium.kotlin.recipesbook.feature.recipe

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import lithium.kotlin.recipesbook.core.domain.RecipesBaseUseCase
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipesBaseUseCase: RecipesBaseUseCase
): ViewModel() {

}