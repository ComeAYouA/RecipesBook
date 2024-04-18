package lithium.kotlin.recipesbook.feature.recipe

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp

@Composable
internal fun RecipeScreen(
    onBackButtonPressed: () -> Unit,
    isLandscape: Boolean
){
    Box(modifier = Modifier.padding(horizontal = 10.dp)){
        if (isLandscape){
            RecipeScreenLandscape(
                onBackButtonPressed = onBackButtonPressed
            )
        } else {
            RecipeScreenPortrait(
                onBackButtonPressed = onBackButtonPressed
            )
        }
    }
}

@Composable
internal fun RecipeScreenPortrait(
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
                headerIsVisible = !headerExpanded.value,
                onBackButtonPressed = onBackButtonPressed
            )

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
                                lerp(1f, 0.92f, translationY.value / translationBounds.floatValue)

                            scaleX = scale
                            scaleY = scale
                            alpha = lerp(1f, 0f, translationY.value / translationBounds.floatValue)
                        }
                )
                recipeImage(modifier = Modifier.padding(vertical = 10.dp))
                firstInfoRow(modifier = Modifier.padding(bottom = 10.dp))
                ingredients()
            }

        }

    }
}

@Composable
internal fun RecipeScreenLandscape(
    onBackButtonPressed: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            headerIsVisible = true,
            onBackButtonPressed = onBackButtonPressed
        )

        Row(
            modifier = Modifier.padding(top = 10.dp)
        ) {
            RecipeImage(
                modifier = Modifier
                    .weight(0.46f)
                    .padding(end = 10.dp)
            )

            RecipeInformation(
                modifier = Modifier.weight(0.54f)
            ){
                firstInfoRow(modifier = Modifier.padding(bottom = 10.dp))
                ingredients()
            }
        }
    }
}

@Composable
internal fun TopBar(
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

        Text(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .graphicsLayer {
                    alpha = headerAnim
                },
            text = "Длиииный Длинный длинннннный Домашний стейк",
            fontSize = 26.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

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

@Preview
@Composable
internal fun RecipeInteraction(
    modifier: Modifier = Modifier
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
                text = "Длиииный Длинный длинннннный Домашний стейк",
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

@Composable
internal fun RecipeImage(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.primary)
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
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.TopCenter),
            text = "Food Type",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
internal fun TimeToCook(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .size(120.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {

    }
}

@Composable
internal fun Ingredients(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1500.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.TopCenter),
            text = "Ingredients",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

internal fun LazyListScope.firstInfoRow(
    modifier: Modifier = Modifier
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
                    .padding(end = 16.dp)
            )

            TimeToCook(
                modifier = Modifier
            )
        }
    }
}

internal fun LazyListScope.ingredients(){
    item {
        Ingredients()
    }
}

internal fun LazyListScope.recipeImage(
    modifier: Modifier = Modifier
){
    item{
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}

internal fun LazyListScope.recipeHeader(
    modifier: Modifier = Modifier
){
    item{
        RecipeInteraction(modifier = modifier)
    }
}