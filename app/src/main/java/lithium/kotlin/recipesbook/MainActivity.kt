package lithium.kotlin.recipesbook

import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import lithium.kotlin.recipesbook.core.ui.theme.RecipesBookTheme
import lithium.kotlin.recipesbook.navigation.RecipesBookNavHost
import lithium.kotlin.recipesbook.ui.AppUiState
import lithium.kotlin.recipesbook.ui.rememberAppUiState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {


            RecipesBookTheme(
                dynamicColor = false
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        windowSize = calculateWindowSizeClass(activity = this)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    windowSize: WindowSizeClass,
    appUiState: AppUiState = rememberAppUiState(windowSizeClass = windowSize)
){
    val backgroundColor = MaterialTheme.colorScheme.surface
    val onBackgroundColor = MaterialTheme.colorScheme.surfaceVariant

    val backgroundGradient = remember {
        Brush.linearGradient(
            0.3f to backgroundColor,
            1.0f to onBackgroundColor,
        )
    }

    val density = LocalDensity.current

    Row(
        modifier = Modifier
            .background(backgroundGradient)
            .padding(top = with(density) {
                ScaffoldDefaults.contentWindowInsets
                    .getTop(density = density)
                    .toDp()
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        if (appUiState.shouldShowNavigationRail){
            NavigationRail(
                modifier = Modifier
                    .padding(start = 52.dp, end = 31.dp)
            )
        }

        Column {
            RecipesBookNavHost(
                modifier = Modifier.weight(1f),
                appUiState = appUiState
            )

            if (appUiState.shouldShowBottomNavigationBar){
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
fun NavigationRail(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        HomeNavigationIcon()
        AddNavigationIcon(Modifier.padding(vertical = 16.dp))
        BookmarksNavigationIcon()
    }
}

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceBright)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeNavigationIcon()
        AddNavigationIcon()
        BookmarksNavigationIcon()
    }
}

@Composable
fun HomeNavigationIcon(
    modifier: Modifier = Modifier,
){
    NavigationIcon(
        modifier = modifier,
        icon = Icons.Default.Home,
        contentDescription = "recipes feed"
    )
}

@Composable
fun AddNavigationIcon(
    modifier: Modifier = Modifier,
){
    NavigationIcon(
        modifier = modifier,
        icon = Icons.Default.Add,
        contentDescription = "add recipe"
    )
}

@Preview
@Composable
fun BookmarksNavigationIcon(
    modifier: Modifier = Modifier,
){
    NavigationIcon(
        modifier = modifier,
        icon = Icons.Default.Favorite,
        contentDescription = "bookmarked recipes"
    )
}

@Composable
fun NavigationIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String
){
    IconButton(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary)
            .size(70.dp, 35.dp)
            .padding(3.dp),
        onClick = {}
    ){
        Icon(
            modifier = Modifier.size(70.dp, 35.dp),
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}