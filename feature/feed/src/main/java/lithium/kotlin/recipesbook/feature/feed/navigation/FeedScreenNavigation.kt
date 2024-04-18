package lithium.kotlin.recipesbook.feature.feed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import lithium.kotlin.recipesbook.feature.feed.FeedScreen

const val FEED_ROUTE = "feed_route"
private const val URI_PATTERN_LINK = "https://www.lithium.kotlin.recipesbook/feed"

fun NavController.navigateToFeedScreen(navOptions: NavOptions)
    = this.navigate(FEED_ROUTE, navOptions)

fun NavGraphBuilder.feedScreen(
    onRecipeClick: () -> Unit
) {
    composable(
        route = FEED_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern =  URI_PATTERN_LINK},
        ),
    ) {
        FeedScreen(
            onRecipeClick = onRecipeClick
        )
    }
}