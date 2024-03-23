package lithium.kotlin.recipesbook.core.domain

import javax.inject.Inject

class RecipesBaseUseCase @Inject constructor(
    val loadRandomRecipesUseCase: LoadRandomRecipesUseCase,
    val loadBookmarksUseCase: LoadBookmarkedRecipes,
    val searchRecipesUseCase: SearchRecipesUseCase,
    val searchRecipesInBookmarksUseCase: SearchRecipesInBookmarksUseCase,
    val addBookmarkedRecipeUseCase: AddBookmarkedRecipeUseCase,
    val deleteBookmarkedRecipeUseCase: DeleteBookmarkedRecipeUseCase
)