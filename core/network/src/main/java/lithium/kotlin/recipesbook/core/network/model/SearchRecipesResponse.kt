package lithium.kotlin.recipesbook.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRecipesResponse (
    @SerialName("results")
    val recipes: List<NetworkRecipePreviewResource>
)