package lithium.kotlin.recipesbook.recipe_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import lithium.kotlin.recipesbook.core.ui.theme.RecipesBookTheme
import lithium.kotlin.recipesbook.feature.feed.Preview
import lithium.kotlin.recipesbook.feature.feed.RecipesFeedViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: RecipesFeedViewModel by viewModels()

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
                    //RecipeScreen(viewModel)
                    Preview(viewModel)
                }
            }
        }
    }
}

