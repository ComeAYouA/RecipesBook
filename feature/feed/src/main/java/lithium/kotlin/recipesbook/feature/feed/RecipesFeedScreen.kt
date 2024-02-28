package lithium.kotlin.recipesbook.feature.feed

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import lithium.kotlin.recipesbook.core.model.DishType
import lithium.kotlin.recipesbook.core.model.Recipe
import lithium.kotlin.recipesbook.core.model.description
import lithium.kotlin.recipesbook.core.ui.R
import lithium.kotlin.recipesbook.core.ui.extension.convertToResource

@Composable
fun RecipeScreen(
    viewModel: RecipesFeedViewModel
){
    val uiState = viewModel.uiState.collectAsState()

    val contentListState = rememberLazyListState()

    val deskFullScreen = remember {
        derivedStateOf {
            if (contentListState.firstVisibleItemIndex == 0){
                contentListState.firstVisibleItemScrollOffset > 200f
            } else {
                true
            }
        }
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ){

        MainScreenBackgroundImage(
            modifier = Modifier.fillMaxWidth()
        )

        Column {
            TopBar(
                modifier = Modifier
                    .fillMaxWidth(),
                isVisible = { deskFullScreen.value },
                onSearchQueryChanged = {query -> viewModel.searchRecipes(query) },
            )

            MainContent(
                modifier = Modifier,
                uiState = uiState.value,
                contentListState = contentListState,
                deskFullScreen = { deskFullScreen.value },
                onRetryButtonClicked = { viewModel.loadRandomRecipes() },
            )
        }

        MainScreenFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = 26.dp
                )
        )
    }
}

@Composable
fun MainScreenBackgroundImage(
    modifier: Modifier = Modifier
){
    Image(
        modifier = modifier,
        painter = painterResource(id = if (isSystemInDarkTheme()) {
            R.drawable.recipe_background_dark
        } else {
            R.drawable.recipe_background
        }
        ),
        contentDescription = "",
        contentScale = ContentScale.FillWidth
    )
}



@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    onSearchQueryChanged: (String) -> Unit,
){
    val focused = remember {
        mutableStateOf(false)
    }

    val background by animateColorAsState(
        targetValue = if (!(isVisible() || focused.value)){
            Color.Transparent
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "SearchView background"
    )


    Box(
        modifier = modifier
            .drawWithContent {
                drawRect(background)
                drawContent()
            }
    ){
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            isVisible = isVisible,
            onSearchQueryChanged = onSearchQueryChanged,
            focus = focused,
        )
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean = { true },
    onSearchQueryChanged: (String) -> Unit,
    focus: MutableState<Boolean>,
){
    val focusRequester = remember {
        FocusRequester()
    }

    val focusManager = LocalFocusManager.current

    val textFieldIsVisible = remember {
        derivedStateOf {
            isVisible() || focus.value
        }
    }

    val visibilityState by animateFloatAsState(
        targetValue = if (textFieldIsVisible.value){
            1f
        }else {
            0f
        },
        label = "searchBarVisibility animation"
    )

    val searchQuery = rememberSaveable {
        mutableStateOf("")
    }

    Row(
        modifier = modifier
    ) {
        BasicTextField(
            modifier = Modifier
                .graphicsLayer {
                    this.alpha = visibilityState
                }
                .padding(start = 10.dp)
                .background(Color.White, RoundedCornerShape(18.dp))
                .padding(8.dp)
                .fillMaxWidth(0.84f)
                .focusable()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    focus.value = focusState.isFocused
                    Log.d("myTag", focusState.isFocused.toString())
                },
            value = searchQuery.value,
            onValueChange = {
                searchQuery .value = it
                onSearchQueryChanged(it)
            },
            singleLine = true,
            textStyle = TextStyle().copy(fontSize = 18.sp),
        )

        IconButton(
            modifier = Modifier
                .padding(start = 10.dp)
                .size(38.dp)
                .align(Alignment.CenterVertically),
            onClick = {
                if (focus.value) focusManager.clearFocus() else focusRequester.requestFocus()
            }
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search recipes button"
            )
        }
    }
}


@Composable
fun MainScreenFloatingButton(
    modifier: Modifier,
){

    val surfaceColor = MaterialTheme.colorScheme.surface
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary

    val gradientBrush = remember {
        Brush.linearGradient(
            0.0f to surfaceColor,
            1.0f to onPrimaryColor,
            start = Offset(182f, 182f),
            end = Offset(0f, 0f)
        )
    }

    Button(
        modifier = modifier,
        shape = RoundedCornerShape(size = 22.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        elevation = ButtonDefaults.buttonElevation(8.dp),
        onClick = { Log.d("myTag", "click") }
    ){
        Box(
            modifier = Modifier
                .background(
                    gradientBrush,
                    RoundedCornerShape(22.dp)
                )
                .size(
                    width = 75.dp,
                    height = 75.dp
                )
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(0.58f),
                painter = painterResource(id = R.drawable.add_icon),
                contentDescription = "Add new Recipe Button"
            )
        }
    }
}


@Composable
fun MainContent(
    modifier: Modifier,
    uiState: lithium.kotlin.recipesbook.feature.feed.RecipeScreenUiState,
    contentListState: LazyListState,
    deskFullScreen: () -> Boolean,
    onRetryButtonClicked: () -> Unit,
){

    val surfaceColor = MaterialTheme.colorScheme.surface
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant

    val gradientBrush = remember {
        Brush.linearGradient(
            0.0f to surfaceColor,
            1.0f to surfaceVariant,
        )
    }

    val tittleVisibilityState = remember {
        derivedStateOf {
            if (contentListState.firstVisibleItemIndex == 0){
                contentListState.firstVisibleItemScrollOffset
            } else {
                100
            }
        }
    }


    val deskSizeAnimation by animateFloatAsState(
        targetValue = if (deskFullScreen()) 1f else 0f,
        label = ""
    )


    Box(
        modifier = modifier
            .graphicsLayer {
                this.translationY = 150f - 150f * deskSizeAnimation
            }
            .background(
                gradientBrush,
                RoundedCornerShape(
                    topStart = lerp(26.dp, 0.dp, deskSizeAnimation),
                    topEnd = lerp(26.dp, 0.dp, deskSizeAnimation)
                )
            )
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        ContentTittle(scrollState = { tittleVisibilityState.value })

        when(uiState){
            is RecipeScreenUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
            is RecipeScreenUiState.Success -> {
                RecipesList(
                    content = uiState.data,
                    modifier = Modifier,
                    listState = contentListState,
                )
            }
            is RecipeScreenUiState.Error -> {
                ErrorMessage(
                    modifier = Modifier
                        .align(Alignment.Center),
                    message = uiState.message,
                    onRetryButtonClicked = onRetryButtonClicked
                )
            }
        }

    }

}

@Composable
fun ContentTittle(
    scrollState: () -> Int
){
    Text(
        modifier = Modifier
            .graphicsLayer {
                val interpolator = lerp(1.dp, 0.dp, scrollState() / 100f).value
                val scale = if (interpolator > 0.8f) interpolator else 0.8f
                this.scaleY = scale
                this.scaleX = scale
                this.alpha = interpolator
            }
            .padding(top = 16.dp),
        text = "Recipes",
        fontSize = 38.sp,
        color = Color.White,
    )
}

@Composable
fun RecipesList(
    content: List<Recipe>,
    modifier: Modifier,
    listState: LazyListState,
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ){
        items(content){ recipe ->
            RecipeItem(
                recipe = recipe,
                modifier = Modifier.padding(top = 36.dp)
            )
        }
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe,
    modifier: Modifier
){
    Card(
        modifier = modifier
            .height(216.dp)
            .fillMaxWidth(0.88f),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(Color.White)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            RecipeCardBackground(
                imageUrl = recipe.imageUrl
            )
            RecipeInfo(
                recipe = recipe,
                modifier = Modifier
                    .padding(
                        start = 18.dp,
                        bottom = 10.dp,
                        end = 18.dp
                    )
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeCardBackground(
    imageUrl: String?
){
    val backgroundColor = MaterialTheme.colorScheme.background

    val whiteGradient = remember {
        Brush.verticalGradient(
            0.0f to Color.Transparent,
            0.84f to backgroundColor
        )
    }

    GlideImage(
        modifier = Modifier
            .fillMaxSize()
            .drawWithContent {
                this.drawContent()
                this.drawRect(whiteGradient)
            },
        model = imageUrl,
        contentDescription = "Recipe img",
        contentScale = ContentScale.Crop,
        transition = CrossFade
    )
}

@Composable
fun RecipeTittle(
    tittle: String,
    modifier: Modifier
){
    Text(
        modifier = modifier
            .padding(bottom = 16.dp),
        text = tittle,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun RecipeInfo(
    recipe: Recipe,
    modifier: Modifier
){
    Box(modifier = modifier){
        RecipeTittle(
            tittle = recipe.tittle ?: "No title to this recipe",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(bottom = 3.dp)
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
fun RecipeAdditionalInfo(
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
fun DishTypes(
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
fun RecipeRatingIcon(
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
fun RecipeRatingAmount(
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
fun TimeIcon(
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
fun TimeAmount(
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
fun ErrorMessage(
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
