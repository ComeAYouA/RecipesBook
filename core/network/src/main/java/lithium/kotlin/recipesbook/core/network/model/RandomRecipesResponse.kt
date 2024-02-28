package lithium.kotlin.recipesbook.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RandomRecipesResponse (
    @SerialName("recipes")
    val recipes: List<NetworkRecipeResource>
)