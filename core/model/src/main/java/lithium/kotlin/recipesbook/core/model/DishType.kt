package lithium.kotlin.recipesbook.core.model


// relocate description to enum class property !!!!!
enum class DishType(
    val description: String
) {
    MainCourse("main course"),
    SideDish("side dish"),
    Dessert("dessert"),
    Appetizer("appetizer"),
    Salad("salad"),
    Bread("bread"),
    Breakfast("breakfast"),
    Soup("soup"),
    Beverage("beverage"),
    Sauce("sauce"),
    Marinade("marinade"),
    Fingerfood("fingerfood"),
    Snack("snack"),
    Drink("drink"),
    Unknown("unknown")
}
