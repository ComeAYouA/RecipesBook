package lithium.kotlin.recipesbook.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import lithium.kotlin.recipesbook.core.model.RecipeInformation

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
    val score: Double?,
    @SerialName("extendedIngredients")
    val ingredients: List<NetworkIngredient>,
)


fun NetworkRecipeResource.asExternalModel(): RecipeInformation =
    RecipeInformation(
        recipeId = this.id,
        tittle = this.tittle,
        imageUrl = this.imageUrl,
        readyInMinutes = this.cookingTimeMinutes,
        dishTypes = this.dishTypes.map { it?.asExternalModel() },
        score = this.score,
        ingredients = this.ingredients.map(NetworkIngredient::asExternalModel)
    )
