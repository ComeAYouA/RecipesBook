package lithium.kotlin.recipesbook.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppUiState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): AppUiState {
    return remember(
        windowSizeClass
    ){
        AppUiState(
            windowSizeClass,
            navController
        )
    }
}

@Stable
class AppUiState(
    private val windowSizeClass: WindowSizeClass,
    val navController: NavHostController
) {
    val shouldShowBottomNavigationBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val shouldShowNavigationRail: Boolean
        get() = !shouldShowBottomNavigationBar
}
