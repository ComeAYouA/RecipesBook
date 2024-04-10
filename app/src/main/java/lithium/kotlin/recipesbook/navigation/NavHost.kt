package lithium.kotlin.recipesbook.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import lithium.kotlin.recipesbook.feature.feed.navigation.FEED_ROUTE
import lithium.kotlin.recipesbook.feature.feed.navigation.feedScreen
import lithium.kotlin.recipesbook.ui.AppUiState


@Composable
fun RecipesBookNavHost(
    modifier: Modifier = Modifier,
    appUiState: AppUiState,
    startDestination: String = FEED_ROUTE,
){
    val navController = appUiState.navController

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        feedScreen()
    }
}