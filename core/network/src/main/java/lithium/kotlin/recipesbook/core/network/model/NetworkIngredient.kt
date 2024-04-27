package lithium.kotlin.recipesbook.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import lithium.kotlin.recipesbook.core.model.Ingredient

@Serializable
data class NetworkIngredient(
    @SerialName("id")
    val id: Int,
    @SerialName("amount")
    val amount: Double,
    @SerialName("image")
    val imageName: String,
    @SerialName("name")
    val name: String,
    @SerialName("original")
    val original: String,
    @SerialName("measures")
    val measures: NetworkMeasures,
)

fun NetworkIngredient.asExternalModel(): Ingredient =
    Ingredient(
        id = this.id,
        amount = this.amount,
        image = this.imageName,
        name = this.name,
        unit = this.original,
        measures = this.measures.asExternalModel()
    )
