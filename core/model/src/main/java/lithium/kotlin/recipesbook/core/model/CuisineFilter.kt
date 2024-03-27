package lithium.kotlin.recipesbook.core.model

data class CuisineFilter(
    override val filterName: String = "Cuisine",
    override val properties: List<FilterProperty> = listOf(
        "African", "Asian", "American", "British", "Cajun", "Caribbean", "Chinese", "Eastern ",
        "European", "European", "French", "German", "Greek", "Indian", "Irish", "Italian",
        "Japanese", "Jewish", "Korean", "Latin", "American", "Mediterranean", "Mexican",
        "Middle Eastern", "Nordic", "Southern", "Spanish", "Thai", "Vietnamese",
    ).map { FilterProperty(name = it, isSelected = false) }
): Filter