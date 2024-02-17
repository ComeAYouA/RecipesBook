package lithium.kotlin.recipesbook.domain.models

import com.google.gson.annotations.SerializedName
import lithium.kotlin.recipesbook.R

enum class DishType {
    @SerializedName("main course")
    MainCourse,
    @SerializedName("side dish")
    SideDish,
    @SerializedName("dessert")
    Dessert,
    @SerializedName("appetizer")
    Appetizer,
    @SerializedName("salad")
    Salad,
    @SerializedName("bread")
    Bread,
    @SerializedName("breakfast")
    Breakfast,
    @SerializedName("soup")
    Soup,
    @SerializedName("beverage")
    Beverage,
    @SerializedName("sauce")
    Sauce,
    @SerializedName("marinade")
    Marinade,
    @SerializedName("fingerfood")
    Fingerfood,
    @SerializedName("snack")
    Snack,
    @SerializedName("drink")
    Drink
}

fun DishType.convertToResource(): Int =
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
    }

fun DishType.description(): String =
    when(this){
        DishType.MainCourse -> "main course"
        DishType.SideDish -> "side dish"
        DishType.Dessert -> "dessert"
        DishType.Appetizer -> "appetizer"
        DishType.Salad -> "salad"
        DishType.Bread -> "bread"
        DishType.Breakfast -> "breakfast"
        DishType.Soup -> "soup"
        DishType.Beverage -> "beverage"
        DishType.Sauce -> "sauce"
        DishType.Marinade -> "marinade"
        DishType.Fingerfood -> "fingerfood"
        DishType.Snack -> "snack"
        DishType.Drink -> "drink"
    }


