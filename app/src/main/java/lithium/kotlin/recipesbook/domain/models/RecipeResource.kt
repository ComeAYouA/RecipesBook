package lithium.kotlin.recipesbook.domain.models

import com.google.gson.annotations.SerializedName

data class RecipeResource(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val tittle: String?,
    @SerializedName("image")
    val imageUrl: String?,
    @SerializedName("readyInMinutes")
    val cookingTimeMinutes: Int?,
    @SerializedName("dishTypes")
    val dishTypes: List<DishType?>,
    @SerializedName("spoonacularScore")
    val score: Double?
)
