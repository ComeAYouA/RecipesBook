package lithium.kotlin.recipesbook.feature.feed

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutQuint
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
import androidx.compose.ui.geometry.CornerRadius
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

//@Composable
//fun RecipeScreen(
//    viewModel: RecipesFeedViewModel
//){
//    val screenUiState = viewModel.screenUiState.collectAsState()
//
//    val contentListState = rememberLazyListState()
//
//    val deskFullScreen = remember {
//        derivedStateOf {
//            if (contentListState.firstVisibleItemIndex == 0){
//                contentListState.firstVisibleItemScrollOffset > 200f
//            } else {
//                true
//            }
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .background(Color.White)
//            .fillMaxSize(),
//    ){
//
//        MainScreenBackgroundImage(
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Column {
//            TopBar(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                isVisible = deskFullScreen,
//                onSearchQueryChanged = {query ->
//                    when(screenUiState.value.content){
//                        RecipeFeedContent.InterestingRecipes, RecipeFeedContent.FoundRecipes -> {
//                            viewModel.searchRandomRecipes(query)
//                        }
//                        RecipeFeedContent.FavoriteRecipes -> {
//                            viewModel.searchBookmarkedRecipes(query)
//                        }
//                    }
//                },
//            )
//
//            MainContent(
//                screenUiState = screenUiState.value,
//                contentUiState = screenUiState.value.contentState,
//                contentListState = contentListState,
//                deskFullScreen = { deskFullScreen.value },
//                onRetryButtonClicked = { viewModel.loadRandomRecipes() },
//                onRecipeBookmarked = {recipe, isBookMarked ->
//                    if (isBookMarked) {
//                        viewModel.bookmarkRecipe(recipe)
//                    } else {
//                        viewModel.unbookmarkRecipe(recipe)
//                    }
//                },
//            )
//        }
//
//        MainScreenFloatingButton(
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(
//                    end = 16.dp,
//                    bottom = 26.dp
//                ),
//            screenUiState = screenUiState.value,
//            onClick = {
//                when(screenUiState.value.content){
//                    RecipeFeedContent.InterestingRecipes, RecipeFeedContent.FoundRecipes -> {
//                        viewModel.loadBookmarks()
//                    }
//                    RecipeFeedContent.FavoriteRecipes -> {
//                        viewModel.loadRandomRecipes()
//                    }
//                }
//            }
//        )
//    }
//}
//
//@Composable
//internal fun MainScreenBackgroundImage(
//    modifier: Modifier = Modifier
//){
//    Image(
//        modifier = modifier,
//        painter = painterResource(id = if (isSystemInDarkTheme()) {
//            R.drawable.recipe_background_dark
//        } else {
//            R.drawable.recipe_background
//        }
//        ),
//        contentDescription = "",
//        contentScale = ContentScale.FillBounds
//    )
//}
//
//
//
//@Composable
//internal fun TopBar(
//    modifier: Modifier = Modifier,
//    isVisible: State<Boolean>,
//    onSearchQueryChanged: (String) -> Unit,
//){
//    val focused = remember {
//        mutableStateOf(false)
//    }
//
//    val background by animateColorAsState(
//        targetValue = if (!(isVisible.value|| focused.value)){
//            Color.Transparent
//        } else {
//            MaterialTheme.colorScheme.surface
//        },
//        label = "SearchView background"
//    )
//
//
//    Box(
//        modifier = modifier
//            .drawWithContent {
//                drawRect(background)
//                drawContent()
//            }
//    ){
//        SearchBar(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            isVisible = isVisible,
//            onSearchQueryChanged = onSearchQueryChanged,
//            focus = focused,
//        )
//    }
//}
//
//@Composable
//internal fun SearchBar(
//    modifier: Modifier = Modifier,
//    isVisible: State<Boolean>,
//    onSearchQueryChanged: (String) -> Unit,
//    focus: MutableState<Boolean>,
//){
//    val focusRequester = remember {
//        FocusRequester()
//    }
//
//    val focusManager = LocalFocusManager.current
//
//    val textFieldIsVisible = remember {
//        derivedStateOf {
//            isVisible.value || focus.value
//        }
//    }
//
//    val visibilityState by animateFloatAsState(
//        targetValue = if (textFieldIsVisible.value){
//            1f
//        }else {
//            0f
//        },
//        label = "searchBarVisibility animation"
//    )
//
//    val searchQuery = rememberSaveable {
//        mutableStateOf("")
//    }
//
//    Row(
//        modifier = modifier
//    ) {
//        BasicTextField(
//            modifier = Modifier
//                .graphicsLayer {
//                    this.alpha = visibilityState
//                }
//                .padding(start = 10.dp)
//                .background(Color.White, RoundedCornerShape(18.dp))
//                .padding(8.dp)
//                .fillMaxWidth(0.84f)
//                .focusable()
//                .focusRequester(focusRequester)
//                .onFocusChanged { focusState ->
//                    focus.value = focusState.isFocused
//                    Log.d("myTag", focusState.isFocused.toString())
//                },
//            value = searchQuery.value,
//            onValueChange = {
//                searchQuery .value = it
//                onSearchQueryChanged(it)
//            },
//            singleLine = true,
//            textStyle = TextStyle().copy(fontSize = 18.sp),
//        )
//
//        IconButton(
//            modifier = Modifier
//                .padding(start = 10.dp)
//                .size(38.dp)
//                .align(Alignment.CenterVertically),
//            onClick = {
//                if (focus.value) focusManager.clearFocus() else focusRequester.requestFocus()
//            }
//        ) {
//            Icon(
//                Icons.Default.Search,
//                contentDescription = "Search recipes button"
//            )
//        }
//    }
//}
//
//
//@Composable
//internal fun MainScreenFloatingButton(
//    modifier: Modifier,
//    screenUiState: RecipeScreenUiState,
//    onClick: () -> Unit
//){
//
//    val secondaryColor = MaterialTheme.colorScheme.secondary
//    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
//
//    val gradientBrush = remember {
//        Brush.linearGradient(
//            0.0f to secondaryColor,
//            1.0f to onPrimaryColor,
//            start = Offset(182f, 182f),
//            end = Offset(0f, 0f)
//        )
//    }
//
//    Button(
//        modifier = modifier,
//        shape = RoundedCornerShape(size = 22.dp),
//        contentPadding = PaddingValues(0.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
//        elevation = ButtonDefaults.buttonElevation(8.dp),
//        onClick = onClick
//    ){
//        Box(
//            modifier = Modifier
//                .background(
//                    gradientBrush,
//                    RoundedCornerShape(22.dp)
//                )
//                .size(
//                    width = 75.dp,
//                    height = 75.dp
//                )
//        ) {
//            when(screenUiState.content){
//                RecipeFeedContent.InterestingRecipes, RecipeFeedContent.FoundRecipes -> {
//                    Icon(
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .fillMaxSize(0.58f),
//                        painter = painterResource(id = R.drawable.ic_bookmark),
//                        tint = Color.White,
//                        contentDescription = "Add new Recipe Button"
//                    )
//                }
//                RecipeFeedContent.FavoriteRecipes -> {
//                    Icon(
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .fillMaxSize(0.58f),
//                        painter = painterResource(id = R.drawable.add_icon),
//                        tint = Color.White,
//                        contentDescription = "Add new Recipe Button"
//                    )
//                }
//            }
//
//        }
//    }
//}
//
//
//@Composable
//internal fun MainContent(
//    modifier: Modifier = Modifier,
//    screenUiState: RecipeScreenUiState,
//    contentUiState: RecipesFeedUiState,
//    contentListState: LazyListState,
//    deskFullScreen: () -> Boolean,
//    onRetryButtonClicked: () -> Unit,
//    onRecipeBookmarked: (Recipe, Boolean) -> Unit,
//){
//
//    Log.d("myTag", "recompose")
//
//    val translationY = remember {
//        Animatable(2400 + 150f)
//    }
//
//    val isFullScreen by remember {
//        derivedStateOf {
//            deskFullScreen()
//        }
//    }
//
//    LaunchedEffect(key1 = screenUiState.content){
//        translationY.animateTo(
//            2400 + 150f,
//            animationSpec = tween(
//                200,
//                easing = EaseInOutQuint
//                )
//        )
//        if (contentListState.firstVisibleItemIndex != 0) {
//            contentListState.scrollToItem(0)
//        }
//        translationY.animateTo(
//            0f,
//            animationSpec = tween(
//                320,
//                easing = EaseOutQuad
//            )
//        )
//    }
//
//    val surfaceColor = MaterialTheme.colorScheme.surface
//    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
//
//    val gradientBrush = remember {
//        Brush.linearGradient(
//            0.0f to surfaceColor,
//            1.0f to surfaceVariant,
//        )
//    }
//
//    val tittleVisibilityState = remember {
//        derivedStateOf {
//            if (contentListState.firstVisibleItemIndex == 0){
//                contentListState.firstVisibleItemScrollOffset
//            } else {
//                100
//            }
//        }
//    }
//
//    val fullScreenTranslation by animateFloatAsState(
//        targetValue = if(isFullScreen) 1f else 0f, label = "deskFullScreen animation"
//    )
//
//    Box(
//        modifier = modifier
//            .graphicsLayer {
//                this.translationY = translationY.value + 150f - 150f * fullScreenTranslation
//            }
//            .drawWithContent {
//                val corner = lerp(98.dp, 0.dp, fullScreenTranslation).value
//                drawRoundRect(
//                    gradientBrush,
//                    cornerRadius = CornerRadius(
//                        corner,
//                        corner
//                    )
//                )
//                drawContent()
//            }
//            .fillMaxSize(),
//        contentAlignment = Alignment.TopCenter
//    ) {
//
//        when(contentUiState){
//            is RecipesFeedUiState.Loading -> CircularProgressIndicator(
//                modifier = Modifier
//                    .align(Alignment.Center)
//            )
//            is RecipesFeedUiState.Success -> {
//                ContentTittle(
//                    screenUiState,
//                    scrollState = { tittleVisibilityState.value }
//                )
//                RecipesList(
//                    content = contentUiState.data,
//                    listState = contentListState,
//                    onRecipeBookmarked = onRecipeBookmarked
//                )
//            }
//            is RecipesFeedUiState.Error -> {
//                ErrorMessage(
//                    modifier = Modifier
//                        .align(Alignment.Center),
//                    message = contentUiState.message,
//                    onRetryButtonClicked = onRetryButtonClicked
//                )
//            }
//        }
//    }
//
//}
//
//@Composable
//internal fun ContentTittle(
//    screenUiState: RecipeScreenUiState,
//    scrollState: () -> Int
//){
//
//    Text(
//        modifier = Modifier
//            .graphicsLayer {
//                val interpolator = lerp(1.dp, 0.dp, scrollState() / 100f).value
//                val scale = if (interpolator > 0.8f) interpolator else 0.8f
//                this.scaleY = scale
//                this.scaleX = scale
//                this.alpha = interpolator
//            }
//            .padding(top = 38.dp),
//        text = screenUiState.content.tittle,
//        fontSize = 28.sp,
//        color = Color.White,
//    )
//}


@Composable
internal fun RecipesList(
    content: List<Recipe>,
    modifier: Modifier = Modifier,
    listState: LazyListState,
    onRecipeBookmarked: (Recipe, Boolean) -> Unit
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 58.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ){
        items(content){ recipe ->
            RecipeItem(
                recipe = recipe,
                modifier = Modifier
                    .padding(top = 36.dp),
                onRecipeBookmarked = onRecipeBookmarked
            )
        }
    }
}

@Composable
internal fun RecipeItem(
    recipe: Recipe,
    modifier: Modifier,
    onRecipeBookmarked: (Recipe, Boolean) -> Unit
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
            BookMark(
                isBookmarked = recipe.isBookmarked,
                recipe = recipe,
                modifier = Modifier
                    .padding(7.dp)
                    .align(Alignment.TopEnd),
                onRecipeBookmarked = onRecipeBookmarked
            )
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun RecipeCardBackground(
    imageUrl: String?
){
    val backgroundColor = MaterialTheme.colorScheme.background

    val whiteGradient = remember {
        Brush.verticalGradient(
            0.0f to Color(0,0,0,20),
            0.92f to backgroundColor
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
