package lithium.kotlin.recipesbook.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRecipesResponse (
    @SerialName("result")
    val recipes: List<NetworkRecipeResource>
)