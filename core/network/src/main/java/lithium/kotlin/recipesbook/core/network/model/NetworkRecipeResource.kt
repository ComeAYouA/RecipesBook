package lithium.kotlin.recipesbook.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import lithium.kotlin.recipesbook.core.model.Recipe

@Serializable
data class NetworkRecipeResource(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val tittle: String?,
    @SerialName("image")
    val imageUrl: String? = null,
    @SerialName("readyInMinutes")
    val cookingTimeMinutes: Int?,
    @SerialName("dishTypes")
    var dishTypes: List<NetworkDishType?>,
    @SerialName("spoonacularScore")
    val score: Double?
)

fun NetworkRecipeResource.asExternalEntity(): Recipe =
    Recipe(
        id = id,
        tittle = tittle,
        imageUrl = imageUrl,
        cookingTimeMinutes = cookingTimeMinutes,
        dishTypes = dishTypes.map { dishType -> dishType?.asExternalEntity() },
        score = score
    )