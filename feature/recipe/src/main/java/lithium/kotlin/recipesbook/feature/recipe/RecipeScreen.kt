package lithium.kotlin.recipesbook.feature.recipe

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.ResourceFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import lithium.kotlin.recipesbook.core.model.DishType
import lithium.kotlin.recipesbook.core.model.Ingredient
import lithium.kotlin.recipesbook.core.ui.R
import lithium.kotlin.recipesbook.core.ui.extension.convertToResource

@Composable
internal fun RecipeScreen(
    viewModel: RecipeViewModel = hiltViewModel(),
    onBackButtonPressed: () -> Unit,
    isLandscape: Boolean
){
    val screenUIState = viewModel.screenUiState.collectAsState()

    Box(modifier = Modifier.padding(horizontal = 10.dp)){
        if (isLandscape){
            RecipeScreenLandscape(
                screenUIState.value,
                onBackButtonPressed = onBackButtonPressed
            )
        } else {
            RecipeScreenPortrait(
                screenUIState.value,
                onBackButtonPressed = onBackButtonPressed
            )
        }
    }
}

@Composable
internal fun RecipeScreenPortrait(
    uiState: RecipeUIState,
    onBackButtonPressed: () -> Unit
){
    val density = LocalDensity.current

    val translationBounds = remember {
        mutableFloatStateOf(with(density){ 300.dp.toPx() })
    }

    val lazyListState = rememberLazyListState()

    val translationY = remember {
        derivedStateOf {
            if (
                lazyListState.firstVisibleItemScrollOffset < translationBounds.floatValue
                && lazyListState.firstVisibleItemIndex == 0
            )
                lazyListState.firstVisibleItemScrollOffset.toFloat()
            else
                translationBounds.floatValue
        }
    }

    val headerExpanded = remember {
        derivedStateOf {
            translationY.value != translationBounds.floatValue
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
    ){
        Column(
            modifier = Modifier
        ) {
            TopBar(
                uiState,
                headerIsVisible = !headerExpanded.value,
                onBackButtonPressed = onBackButtonPressed
            )

            when(uiState){
                is RecipeUIState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is RecipeUIState.Success -> {
                    RecipeInformation(
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        state = lazyListState
                    ){
                        recipeHeader(
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .onPlaced {
                                    translationBounds.floatValue =
                                        it.size.height.toFloat() - with(density) { 40.dp.toPx() }
                                }
                                .graphicsLayer {
                                    val scale =
                                        lerp(
                                            1f,
                                            0.92f,
                                            translationY.value / translationBounds.floatValue
                                        )

                                    scaleX = scale
                                    scaleY = scale
                                    alpha = lerp(
                                        1f,
                                        0f,
                                        translationY.value / translationBounds.floatValue
                                    )
                                },
                            header = uiState.data.tittle?:""
                        )
                        recipeImage(
                            modifier = Modifier.padding(vertical = 10.dp),
                            uiState.data.imageUrl
                        )
                        firstInfoRow(
                            modifier = Modifier.padding(bottom = 10.dp),
                            dishType = uiState.data.dishTypes,
                            timeToCook = uiState.data.readyInMinutes?:0
                        )
                        ingredients(uiState.data.ingredients)
                    }
                }
                else -> {}
            }


        }

    }
}

@Composable
internal fun RecipeScreenLandscape(
    uiState: RecipeUIState,
    onBackButtonPressed: () -> Unit
){

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            uiState,
            headerIsVisible = true,
            onBackButtonPressed = onBackButtonPressed
        )

        when(uiState){
            is RecipeUIState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is RecipeUIState.Success -> {
                Row(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    RecipeImage(
                        modifier = Modifier
                            .weight(0.46f)
                            .padding(end = 10.dp),
                        uiState.data.imageUrl
                    )

                    RecipeInformation(
                        modifier = Modifier.weight(0.54f)
                    ){
                        firstInfoRow(
                            modifier = Modifier.padding(bottom = 10.dp),
                            dishType = uiState.data.dishTypes,
                            timeToCook = uiState.data.readyInMinutes?:0
                        )
                        ingredients(uiState.data.ingredients)
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
internal fun TopBar(
    uiState: RecipeUIState,
    modifier: Modifier = Modifier,
    headerIsVisible: Boolean = false,
    onBackButtonPressed: () -> Unit
){

    val headerAnim by animateFloatAsState(
        targetValue = if (headerIsVisible) 1f else 0f, label = ""
    )

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = onBackButtonPressed
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "back to feed"
            )
        }

        when(uiState){
            is RecipeUIState.Success -> {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .graphicsLayer {
                            alpha = headerAnim
                        },
                    text = uiState.data.tittle?:"",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            else -> {}
        }


        CookRecipeButton(
            modifier = Modifier
                .graphicsLayer {
                    alpha = headerAnim
                }
                .padding(end = 32.dp),
            iconSize = 20.dp
        )
    }
}

@Composable
internal fun RecipeInteraction(
    modifier: Modifier = Modifier,
    header: String
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.82f),
                text = header,
                textAlign = TextAlign.Justify,
                lineHeight = 32.sp,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black
            )

            CookRecipeButton(
                modifier = Modifier,
                iconSize = 32.dp
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun RecipeImage(
    modifier: Modifier = Modifier,
    imageUrl: String?
){
    val density = LocalDensity.current

    val width = remember {
        mutableIntStateOf(0)
    }

    GlideImage(
        modifier = modifier
            .onPlaced {
                width.intValue = it.size.width
            }
            .fillMaxWidth()
            .height(
                with(density) {
                    (width.intValue / 4 * 3).toDp()
                }
            )
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.primary),
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
internal fun RecipeInformation(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit
){
    LazyColumn(
        modifier = modifier,
        state = state,
        content = content,
        contentPadding = PaddingValues(bottom = 21.dp)
    )
}

@Composable
internal fun CookRecipeButton(
    modifier: Modifier = Modifier,
    iconSize: Dp
){
    IconButton(
        modifier = modifier
            .clip(CircleShape)
            .size(iconSize + 10.dp)
            .background(MaterialTheme.colorScheme.primary),
        onClick = { /*TODO*/ }
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "back to feed"
        )
    }
}

@Composable
internal fun FoodType(
    modifier: Modifier = Modifier,
    dishTypes: List<DishType?>
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row (
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            dishTypes.map { type ->
                type?.let { dishType ->
                    dishType.convertToResource()?.let { resource ->
                        Image(
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .fillMaxHeight(0.4f)
                                .weight(1f),
                            painter = painterResource(id = resource),
                            contentDescription = type.description
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun TimeToCook(
    modifier: Modifier = Modifier,
    timeToCook: Int = 0
){
    Column(
        modifier = modifier
            .size(120.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, true),
            painter = painterResource(id = R.drawable.ic_time),
            contentDescription = "time to cook"
        )
        Text(
            text = timeToCook.toString(),
            fontSize = 18.sp
        )
    }
}

@Composable
internal fun Ingredients(
    modifier: Modifier = Modifier,
    ingredients: List<Ingredient>
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.CenterHorizontally),
            text = "Ingredients",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Column {
            ingredients.forEach{ ingredient ->
                Box(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(end = 6.dp),
                        text = "${ingredient.name}: ${ingredient.amount} ${ingredient.measures.metric.shortName}",
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

internal fun LazyListScope.firstInfoRow(
    modifier: Modifier = Modifier,
    dishType: List<DishType?> = listOf(),
    timeToCook: Int = 0

){
    item {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FoodType(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                dishType
            )

            TimeToCook(
                modifier = Modifier.align(Alignment.CenterVertically),
                timeToCook
            )
        }
    }
}

internal fun LazyListScope.ingredients(
    ingredients: List<Ingredient>
){
    item {
        Ingredients(
            ingredients = ingredients
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
internal fun LazyListScope.recipeImage(
    modifier: Modifier = Modifier,
    imageUrl: String?
){
    item{
        GlideImage(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .clip(RoundedCornerShape(18.dp))
                .background(MaterialTheme.colorScheme.primary),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

internal fun LazyListScope.recipeHeader(
    modifier: Modifier = Modifier,
    header: String
){
    item{
        RecipeInteraction(
            modifier = modifier,
            header = header
        )
    }
}