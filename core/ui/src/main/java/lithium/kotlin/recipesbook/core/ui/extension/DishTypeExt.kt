package lithium.kotlin.recipesbook.core.ui.extension

import lithium.kotlin.recipesbook.core.model.DishType
import lithium.kotlin.recipesbook.core.ui.R

fun DishType.convertToResource(): Int? =
    when(this){
        DishType.MainCourse -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_main_course
        DishType.SideDish -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_side_dish
        DishType.Dessert -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_dessert
        DishType.Appetizer -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_appetizer
        DishType.Salad -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_salad
        DishType.Bread -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_bread
        DishType.Breakfast -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_breakfast
        DishType.Soup -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_soupe
        DishType.Beverage -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_beverage
        DishType.Sauce -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_suace
        DishType.Marinade -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_marinade
        DishType.Fingerfood -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_fingerfood
        DishType.Snack -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_snack
        DishType.Drink -> lithium.kotlin.recipesbook.core.ui.R.drawable.ic_drink
        else -> null
    }