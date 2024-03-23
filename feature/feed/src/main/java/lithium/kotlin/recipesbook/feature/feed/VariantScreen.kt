package lithium.kotlin.recipesbook.feature.feed

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import lithium.kotlin.recipesbook.core.model.DishType
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.ui.R

@Composable
fun Preview(
    viewModel: RecipesFeedViewModel
){

    val backgroundColor = MaterialTheme.colorScheme.surface
    val onBackgroundColor = MaterialTheme.colorScheme.surfaceVariant

    val backgroundGradient = remember {
        Brush.linearGradient(
            0.3f to backgroundColor,
            1.0f to onBackgroundColor,
        )
    }

    val settingsExpanded = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .background(backgroundGradient)
            .fillMaxSize()
    ){
        SearchBar(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 47.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
            onSearchQueryChanged = { query -> viewModel.searchRandomRecipes(query)},
            onSettingsButtonClicked = {settingsExpanded.value = !settingsExpanded.value}
        )

        FiltersList(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp),
            filters = listOf(
                RecipesFeedFilter("Diet", listOf("Gluten Free", "Vegeterian", "Paleon", "Pescetarian")),
                RecipesFeedFilter("Cousine", listOf("Asian", "African", "American", "Chinese"))
            ),
            isVisible = { settingsExpanded.value }
        )


        Feed(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            viewModel = viewModel
        )

    }
}


@Preview
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit = {},
    onSettingsButtonClicked: () -> Unit = {}
){
    val searchQuery = rememberSaveable {
        mutableStateOf("")
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ){
        BasicTextField(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(18.dp))
                .height(40.dp)
                .padding(8.dp)
                .fillMaxWidth(0.8f),
            value = searchQuery.value,
            onValueChange = {
                searchQuery.value = it
                onSearchQueryChanged(it)
            },
            singleLine = true,
            textStyle = TextStyle().copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp
            ),
            cursorBrush = Brush.linearGradient(
                0.0f to MaterialTheme.colorScheme.onBackground,
                1.0f to MaterialTheme.colorScheme.onBackground
            ),
            decorationBox = { innerTextField ->
                Row{
                    Icon(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    ){
                        innerTextField()
                    }
                }
            }
        )

        FilterButton(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .size(40.dp)
                .align(Alignment.CenterVertically),
            onClick = onSettingsButtonClicked
        )

    }

}



@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier
                .size(18.dp),
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = "settings"
        )
    }

}


@Composable
fun FiltersList(
    modifier: Modifier = Modifier,
    filters: List<RecipesFeedFilter>,
    isVisible: () -> Boolean
){

    val launchAnimationProgression = animateFloatAsState(
        targetValue = if (isVisible()) 1f else 0f, label = "filterList animation"
    )

    Column(
        modifier = modifier
            .layout { measurable, constraints ->

                val placeable = measurable.measure(constraints)

                layout(height = (placeable.height * launchAnimationProgression.value).toInt(), width = placeable.width){
                    placeable.place(0,0)
                }
            }
            .graphicsLayer {
                this.alpha = launchAnimationProgression.value
            }
    ) {
        filters.forEach{
            FilterItem(
                modifier = Modifier.padding(bottom = 6.dp),
                filter = it
            )
        }
    }
}

@Composable
fun FilterItem(
    modifier: Modifier = Modifier,
    filter: RecipesFeedFilter
){

    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(bottom = 6.dp, start = 14.dp),
            text = filter.name,
            fontSize = 18.sp,
            )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp)
        ){
            items(filter.properties){ property ->
                FilterProperty(
                    modifier = Modifier.padding(end = 6.dp),
                    propertyName = property
                )
            }
        }
    }
}

@Composable
fun FilterProperty(
    modifier: Modifier = Modifier,
    propertyName: String
){
    val isSelected = remember {
        mutableStateOf(false)
    }

    Button(
        modifier = modifier
            .background(Color.Transparent),
        colors = ButtonDefaults.buttonColors(if (!isSelected.value) MaterialTheme.colorScheme.primary else Color(97, 193, 125)),
        onClick = { isSelected.value = !isSelected.value }
    ) {
        Text(
            modifier = Modifier,
            text = propertyName
        )
    }

}




@Composable
internal fun Feed(
    modifier: Modifier = Modifier,
    viewModel: RecipesFeedViewModel
){

    val contentUiState = viewModel.screenUiState.collectAsState()

    contentUiState.value.let { state ->
        when(state){
            is RecipesFeedUiState.Loading -> CircularProgressIndicator(
                modifier = modifier
            )
            is RecipesFeedUiState.Success -> {
                LazyColumn(
                    modifier = modifier,
                    contentPadding = PaddingValues(vertical = 18.dp)
                ){
                    items(state.data){ recipe ->
                        Item(
                            modifier = Modifier.padding(bottom = 22.dp),
                            recipe = recipe
                        )
                    }
                }
            }
            is RecipesFeedUiState.Error -> {
                ErrorMessage(
                    modifier = modifier,
                    message = state.message,
                    onRetryButtonClicked = {  }
                )
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Item(
    modifier: Modifier = Modifier,
    recipe: Recipe = Recipe(
        isBookmarked = false,
        id = 10L,
        imageUrl = null,
        tittle = "000000000000000000000000000000000000",
        cookingTimeMinutes = 45,
        dishTypes = listOf(DishType.Appetizer),
        score = 10.0
    )
) {
    Card(
        modifier = modifier
            .height(280.dp)
            .fillMaxWidth(0.88f),
        shape = RoundedCornerShape(26.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
        ){
            GlideImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = recipe.imageUrl,
                contentDescription = "Recipe img",
                contentScale = ContentScale.Crop,
                transition = CrossFade
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RecipeInfo(
                    recipe = recipe,
                    modifier = Modifier
                        .fillMaxWidth(0.84f)
                        .padding(
                            start = 18.dp,
                            bottom = 10.dp,
                            end = 18.dp,
                            top = 8.dp
                        )

                )
                BookMark(
                    isBookmarked = recipe.isBookmarked,
                    recipe = recipe,
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(end = 10.dp, bottom = 8.dp),
                    onRecipeBookmarked = { _, _ -> }
                )
            }
        }
    }
}

