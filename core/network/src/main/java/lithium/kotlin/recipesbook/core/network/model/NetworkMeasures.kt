package lithium.kotlin.recipesbook.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import lithium.kotlin.recipesbook.core.model.Measure
import lithium.kotlin.recipesbook.core.model.Measures

@Serializable
data class NetworkMeasures(
    @SerialName("metric")
    val metric: NetworkMeasure,
    @SerialName("us")
    val us: NetworkMeasure
)

@Serializable
data class NetworkMeasure(
    @SerialName("amount")
    val amount: Double,
    @SerialName("unitLong")
    val longName: String,
    @SerialName("unitShort")
    val shortName: String
)

fun NetworkMeasures.asExternalModel(): Measures =
    Measures(
        metric = this.metric.asExternalModel(),
        us = this.us.asExternalModel()
    )

fun NetworkMeasure.asExternalModel(): Measure =
    Measure(
        amount = this.amount,
        longName = this.longName,
        shortName = this.shortName
    )
