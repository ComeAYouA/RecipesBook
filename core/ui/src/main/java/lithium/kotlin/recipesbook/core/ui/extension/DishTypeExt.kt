package lithium.kotlin.recipesbook.core.ui.extension

import lithium.kotlin.recipesbook.core.model.DishType
import lithium.kotlin.recipesbook.core.ui.R

fun DishType.convertToResource(): Int? =
    when(this){
        DishType.MainCourse -> R.drawable.ic_main_course
        DishType.SideDish -> R.drawable.ic_side_dish
        DishType.Dessert -> R.drawable.ic_dessert
        DishType.Appetizer -> R.drawable.ic_appetizer
        DishType.Salad -> R.drawable.ic_salad
        DishType.Bread -> R.drawable.ic_bread
        DishType.Breakfast -> R.drawable.ic_breakfast
        DishType.Soup -> R.drawable.ic_soupe
        DishType.Beverage -> R.drawable.ic_beverage
        DishType.Sauce -> R.drawable.ic_suace
        DishType.Marinade -> R.drawable.ic_marinade
        DishType.Fingerfood -> R.drawable.ic_fingerfood
        DishType.Snack -> R.drawable.ic_snack
        DishType.Drink -> R.drawable.ic_drink
        DishType.Unknown -> null
    }