package lithium.kotlin.recipesbook.core.model

enum class DishType {
    MainCourse,
    SideDish,
    Dessert,
    Appetizer,
    Salad,
    Bread,
    Breakfast,
    Soup,
    Beverage,
    Sauce,
    Marinade,
    Fingerfood,
    Snack,
    Drink,
    Unknown
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
        else -> {"unknown"}
    }