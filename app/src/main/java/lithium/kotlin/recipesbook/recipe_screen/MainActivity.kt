package lithium.kotlin.recipesbook.recipe_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import lithium.kotlin.recipesbook.core.ui.theme.RecipesBookTheme
import lithium.kotlin.recipesbook.feature.feed.RecipeFeedScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


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
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(){
    Box {
        RecipeFeedScreen()

        NavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            )

    }
}

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean = {true},
) {
    val windowHeight = LocalConfiguration.current.screenHeightDp

    val translationY by animateFloatAsState(
        targetValue = if (isVisible()){
            0f
        } else {
            windowHeight.toFloat()
        }, label = ""
    )


    Row(
        modifier = modifier
            .graphicsLayer {
                this.translationY = translationY
            }
            .background(MaterialTheme.colorScheme.surfaceBright)
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationIcon(
            icon = Icons.Default.Home,
            contentDescription = "recipes feed"
        )
        NavigationIcon(
            icon = Icons.Default.Add,
            contentDescription = "add recipe"
        )
        NavigationIcon(
            icon = Icons.Default.Favorite,
            contentDescription = "bookmarked recipe"
        )
    }
}

@Composable
fun NavigationIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String
){
    IconButton(
        modifier = modifier
            .size(70.dp, 35.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary),
        onClick = {}
    ){
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}