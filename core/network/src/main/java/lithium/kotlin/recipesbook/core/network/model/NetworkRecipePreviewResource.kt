package lithium.kotlin.recipesbook.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import lithium.kotlin.recipesbook.core.model.RecipePreview

@Serializable
data class NetworkRecipePreviewResource(
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
    val score: Double?,
)

fun NetworkRecipePreviewResource.asExternalModel(): RecipePreview =
    RecipePreview(
        id = id,
        tittle = tittle,
        imageUrl = imageUrl,
        cookingTimeMinutes = cookingTimeMinutes,
        dishTypes = dishTypes.map { dishType -> dishType?.asExternalModel() },
        score = score
    )