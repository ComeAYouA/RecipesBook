package lithium.kotlin.recipesbook.feature.feed

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import lithium.kotlin.recipesbook.core.model.DishType
import lithium.kotlin.recipesbook.core.model.Filter
import lithium.kotlin.recipesbook.core.model.FilterProperty
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.description
import lithium.kotlin.recipesbook.core.ui.R
import lithium.kotlin.recipesbook.core.ui.extension.convertToResource

@Composable
fun RecipeFeedScreen(
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

    val filterListExpanded = rememberSaveable{
        mutableStateOf(false)
    }

    val searchQuery = rememberSaveable {
        mutableStateOf("")
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
                )
                .fillMaxWidth(),
            onSearchQueryChanged = { query -> viewModel.searchRecipes(query)},
            onFilterButtonClicked = {filterListExpanded.value = !filterListExpanded.value},
            searchQuery = searchQuery
        )

        FiltersList(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 12.dp, bottom = 12.dp),
            filters = viewModel.recipesFeedFilters,
            isVisible = { filterListExpanded.value },
            onFilterSelected = {filter, property, isSelected ->
                viewModel.changeFilter(filter, property, isSelected)
                viewModel.searchRecipes(searchQuery.value)
            }
        )


        RecipesFeed(
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
    onFilterButtonClicked: () -> Unit = {},
    searchQuery: MutableState<String> = rememberSaveable { mutableStateOf("") }
){

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
            onClick = onFilterButtonClicked
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
    filters: List<Filter>,
    isVisible: () -> Boolean,
    onFilterSelected: (Filter, FilterProperty, Boolean) -> Unit
){

    val launchAnimationProgression = animateFloatAsState(
        targetValue = if (isVisible()) 1f else 0f, label = "filterList animation"
    )

    val filterListHeight = remember {
        mutableIntStateOf(0)
    }


    Column(
        modifier = modifier
            .layout { measurable, constraints ->

                val placeable = measurable.measure(constraints)

                if (placeable.height != 0) filterListHeight.intValue = placeable.height

                layout(
                    height = (filterListHeight.intValue * launchAnimationProgression.value).toInt(),
                    width = placeable.width
                ) {
                    placeable.place(0, 0)
                }
            }
            .graphicsLayer {
                this.alpha = launchAnimationProgression.value
            }
    ) {
        if (isVisible()) filters.forEach{
            FilterItem(
                modifier = Modifier.padding(bottom = 6.dp),
                filter = it,
                onFilterSelected = onFilterSelected
            )
        }
    }
}

@Composable
fun FilterItem(
    modifier: Modifier = Modifier,
    filter: Filter,
    onFilterSelected: (Filter, FilterProperty, Boolean) -> Unit
){

    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(bottom = 6.dp, start = 14.dp),
            text = filter.filterName,
            fontSize = 18.sp,
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp)
        ){
            items(filter.properties){ property ->
                FilterBox(
                    modifier = Modifier.padding(end = 6.dp),
                    property = property,
                    onPropertySelected = {filterProperty, isSelected -> onFilterSelected(filter, filterProperty, isSelected)}
                )
            }
        }
    }
}

@Composable
fun FilterBox(
    modifier: Modifier = Modifier,
    property: FilterProperty,
    onPropertySelected: (FilterProperty, Boolean) -> Unit
){
    val isSelected = remember {
        mutableStateOf(property.isSelected)
    }

    Button(
        modifier = modifier
            .background(Color.Transparent),
        colors = ButtonDefaults.buttonColors(if (!isSelected.value) MaterialTheme.colorScheme.primary else Color(97, 193, 125)),
        onClick = {
            isSelected.value = !isSelected.value
            onPropertySelected(property, isSelected.value)
        }
    ) {
        Text(
            modifier = Modifier,
            text = property.name
        )
    }

}

@Composable
internal fun RecipesFeed(
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
                        RecipeItem(
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
fun RecipeItem(
    modifier: Modifier = Modifier,
    recipe: Recipe
) {
    Card(
        modifier = modifier
            .height(280.dp)
            .fillMaxWidth(0.92f),
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


@Composable
fun BookMark(
    isBookmarked: Boolean,
    recipe: Recipe,
    modifier: Modifier,
    onRecipeBookmarked: (Recipe, Boolean) -> Unit
) {
    val bookmark = remember {
        mutableStateOf(isBookmarked)
    }

    IconButton(
        modifier = modifier,
        onClick = {
            bookmark.value = !bookmark.value
            onRecipeBookmarked(recipe, bookmark.value)
        },
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            imageVector = if (bookmark.value) {
                Icons.Default.Favorite
            }else {
                Icons.Default.FavoriteBorder
            },
            tint = if (bookmark.value) Color.Red else MaterialTheme.colorScheme.onSurface,
            contentDescription = "bookmark"
        )
    }
}

@Composable
internal fun RecipeTittle(
    tittle: String,
    modifier: Modifier
){
    Text(
        modifier = modifier
            .padding(bottom = 16.dp),
        text = tittle,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
internal fun RecipeInfo(
    recipe: Recipe,
    modifier: Modifier
){
    Box(modifier = modifier){
        RecipeTittle(
            tittle = recipe.tittle ?: "No title to this recipe",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(bottom = 6.dp)
        )
        RecipeAdditionalInfo(
            modifier = Modifier
                .align(Alignment.BottomStart),
            readyInMinutes = recipe.cookingTimeMinutes,
            dishTypes = recipe.dishTypes,
            rating = recipe.score?.toInt()
        )
    }
}


@Composable
internal fun RecipeAdditionalInfo(
    rating: Int?,
    dishTypes: List<DishType?>,
    readyInMinutes: Int?,
    modifier: Modifier
){
    Row(
        modifier = modifier
    ){
        RecipeRatingIcon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        RecipeRatingAmount(
            modifier = Modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterVertically),
            rating = rating
        )
        TimeIcon(
            modifier = Modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically))
        TimeAmount(
            modifier = Modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterVertically),
            minutes = readyInMinutes
        )
        DishTypes(
            modifier = Modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            content = dishTypes
        )
    }
}

@Composable
internal fun DishTypes(
    modifier: Modifier = Modifier,
    content: List<DishType?>
){
    Row (
        modifier = modifier
    ) {
        content.map {type ->
            type?.let { dishType ->
                dishType.convertToResource()?.let { resource ->
                    Image(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(18.dp),
                        painter = painterResource(id = resource),
                        contentDescription = type.description()
                    )
                }
            }
        }
    }
}

@Composable
internal fun RecipeRatingIcon(
    modifier: Modifier = Modifier
){
    Image(
        modifier = modifier
            .size(14.dp, 14.dp),
        painter = painterResource(id = R.drawable.ic_star),
        contentDescription = "",
    )
}

@Composable
internal fun RecipeRatingAmount(
    rating: Int?,
    modifier: Modifier = Modifier
){
    Text(
        modifier = modifier,
        text = "$rating/100",
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
    )
}

@Composable
internal fun TimeIcon(
    modifier: Modifier = Modifier
){
    Image(
        modifier = modifier
            .size(14.dp, 14.dp),
        painter = painterResource(id = R.drawable.ic_clock),
        contentDescription = "",
    )
}

@Composable
internal fun TimeAmount(
    minutes: Int?,
    modifier: Modifier = Modifier
){
    Text(
        modifier = modifier,
        text = "${minutes?: "?"} мин",
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
    )
}

@Composable
internal fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
    onRetryButtonClicked: () -> Unit = {}
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onRetryButtonClicked
        ) {
            Text(
                text = "Retry",
                color = Color.White
            )
        }
    }
}
