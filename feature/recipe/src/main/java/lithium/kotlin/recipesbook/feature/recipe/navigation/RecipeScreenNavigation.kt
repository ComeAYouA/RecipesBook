package lithium.kotlin.recipesbook.feature.recipe.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import lithium.kotlin.recipesbook.feature.recipe.RecipeScreen
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

internal const val RECIPE_ID = "recipeId"

private val URL_CHARACTER_ENCODING = UTF_8.name()

internal class RecipeArgs(val recipeId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(URLDecoder.decode(checkNotNull(savedStateHandle[RECIPE_ID]), URL_CHARACTER_ENCODING))
}

const val RECIPE_ROUTE = "recipe_route"

fun NavController.navigateToRecipeScreen(recipeId: Long) {
    val encodedId = URLEncoder.encode(recipeId.toString(), URL_CHARACTER_ENCODING)
    navigate("$RECIPE_ROUTE/$encodedId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.recipeScreen(
    isLandscape: Boolean = false,
    onBackButtonPressed: () -> Unit,
) {
    composable(
        route = "$RECIPE_ROUTE/{${RECIPE_ID}}",
        arguments = listOf(
            navArgument(RECIPE_ID) { type = NavType.StringType }
        ),
    ) {
        RecipeScreen(
            isLandscape = isLandscape,
            onBackButtonPressed = onBackButtonPressed
        )
    }
}