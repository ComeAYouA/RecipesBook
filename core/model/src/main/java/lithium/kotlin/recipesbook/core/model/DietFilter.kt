package lithium.kotlin.recipesbook.core.model

data class DietFilter(
    override val filterName: String = "Diet",
    override val properties: List<FilterProperty> = listOf(
        "Gluten Free", "Ketogenic", "Vegetarian", "Lacto-Vegetarian", "Ovo-Vegetarian", "Vegan",
        "Pescetarian", "Paleo", "Primal", "Low FODMAP", "Whole30"
    ).map { FilterProperty(name = it, isSelected = false) }
): Filter