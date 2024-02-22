package lithium.kotlin.recipesbook.domain.models

import com.google.gson.annotations.SerializedName

data class RecipesSearchResponse(
    @SerializedName("results")
    val result: List<RecipeResource>
)