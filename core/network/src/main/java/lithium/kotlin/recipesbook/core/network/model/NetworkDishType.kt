package lithium.kotlin.recipesbook.core.network.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import lithium.kotlin.recipesbook.core.model.DishType

@Serializable(with = DishTypeSerializer::class)
enum class NetworkDishType {
    @SerialName("main course")
    MainCourse,
    @SerialName("side dish")
    SideDish,
    @SerialName("dessert")
    Dessert,
    @SerialName("appetizer")
    Appetizer,
    @SerialName("salad")
    Salad,
    @SerialName("bread")
    Bread,
    @SerialName("breakfast")
    Breakfast,
    @SerialName("soup")
    Soup,
    @SerialName("beverage")
    Beverage,
    @SerialName("sauce")
    Sauce,
    @SerialName("marinade")
    Marinade,
    @SerialName("fingerfood")
    Fingerfood,
    @SerialName("snack")
    Snack,
    @SerialName("drink")
    Drink,
    @SerialName("unknown")
    Unknown
}

fun NetworkDishType.asExternalModel(): DishType =
    when(this){
        NetworkDishType.MainCourse -> DishType.MainCourse
        NetworkDishType.Appetizer -> DishType.Appetizer
        NetworkDishType.Beverage -> DishType.Beverage
        NetworkDishType.Breakfast -> DishType.Breakfast
        NetworkDishType.Bread -> DishType.Bread
        NetworkDishType.Dessert -> DishType.Dessert
        NetworkDishType.Drink -> DishType.Drink
        NetworkDishType.Fingerfood -> DishType.Fingerfood
        NetworkDishType.Marinade -> DishType.Marinade
        NetworkDishType.Salad -> DishType.Salad
        NetworkDishType.SideDish -> DishType.SideDish
        NetworkDishType.Sauce -> DishType.Sauce
        NetworkDishType.Snack -> DishType.Snack
        NetworkDishType.Soup -> DishType.Soup
        NetworkDishType.Unknown -> DishType.Unknown
    }

internal val NetworkDishType.serialName: String
    get() = this::class.java.getField(this.name).getAnnotation(SerialName::class.java)!!.value
internal object DishTypeSerializer : KSerializer<NetworkDishType> {
    private val className = this::class.qualifiedName!!
    private val lookup = NetworkDishType.values().associateBy({ it }, { it.serialName })
    private val revLookup = NetworkDishType.values().associateBy { it.serialName }
    override fun serialize(encoder: Encoder, value: NetworkDishType) = encoder.encodeString(lookup.getValue(value))
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(className, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder) = revLookup.getOrDefault(decoder.decodeString(),
        NetworkDishType.Unknown
    )
}