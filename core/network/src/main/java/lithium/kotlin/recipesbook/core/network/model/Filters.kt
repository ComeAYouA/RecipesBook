package lithium.kotlin.recipesbook.core.network.model

import lithium.kotlin.recipesbook.core.model.CuisineFilter
import lithium.kotlin.recipesbook.core.model.DietFilter
import lithium.kotlin.recipesbook.core.model.Filter

val List<Filter>.cuisineFilter: CuisineFilter?
    get() = this.firstOrNull { it is CuisineFilter } as CuisineFilter?
val List<Filter>.dietFilter: DietFilter?
    get() = this.firstOrNull { it is DietFilter } as DietFilter?

fun Filter.toNetworkQuery(): String
        = this.properties.filter { it.isSelected }.joinToString(", ") { it.name }
