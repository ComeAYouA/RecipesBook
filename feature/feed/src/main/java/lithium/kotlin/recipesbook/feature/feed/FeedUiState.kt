package lithium.kotlin.recipesbook.feature.feed

import lithium.kotlin.recipesbook.core.model.Recipe

internal sealed interface FeedUiState {
    object Loading : FeedUiState
    data class Success(
        val data: List<Recipe>
    ) : FeedUiState
    data class Error(
        val message: String
    ) : FeedUiState
}