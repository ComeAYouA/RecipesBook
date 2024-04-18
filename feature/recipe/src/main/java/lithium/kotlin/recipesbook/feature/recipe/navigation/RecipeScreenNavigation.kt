package lithium.kotlin.recipesbook.feature.recipe.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import lithium.kotlin.recipesbook.feature.recipe.RecipeScreen

const val RECIPE_ROUTE = "recipe_route"
private const val URI_PATTERN_LINK = "https://www.lithium.kotlin.recipesbook/recipe"

fun NavController.navigateToRecipeScreen()
        = this.navigate(RECIPE_ROUTE)

fun NavGraphBuilder.recipeScreen(
    isLandscape: Boolean = false,
    onBackButtonPressed: () -> Unit
) {
    composable(
        route = RECIPE_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern =  URI_PATTERN_LINK},
        ),
    ) {
        RecipeScreen(
            isLandscape = isLandscape,
            onBackButtonPressed = onBackButtonPressed
        )
    }
}