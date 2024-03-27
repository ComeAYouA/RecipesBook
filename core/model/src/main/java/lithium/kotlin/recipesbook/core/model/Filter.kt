package lithium.kotlin.recipesbook.core.model

interface Filter {
    val filterName: String
    val properties: List<FilterProperty>
}

data class FilterProperty(
    val name: String,
    var isSelected: Boolean
)