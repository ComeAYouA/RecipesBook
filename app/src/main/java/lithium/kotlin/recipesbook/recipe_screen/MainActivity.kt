package lithium.kotlin.recipesbook.recipe_screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import lithium.kotlin.recipesbook.R
import lithium.kotlin.recipesbook.ui.theme.RecipesBookTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun RecipeScreen(

){

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
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.recipe_background),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )

        Column {
            TopBar(
                modifier = Modifier
                    .fillMaxWidth(),
                expanded =  { deskFullScreen.value }
            )

            MainContent(
                modifier = Modifier,
                contentListState
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
fun TopBar(
    modifier: Modifier = Modifier,
    expanded: () -> Boolean
){
    val background by animateColorAsState(
        targetValue = if (!expanded()){
            Color.Transparent
        } else {
            Color(149, 219, 0)
        },
        label = "SearchView background"
    )

    Row (
        modifier = modifier
            .background(background),
        horizontalArrangement = Arrangement.End
    ){
        IconButton(
            onClick = {}
        ){
            Icon(Icons.Default.Search, contentDescription = "Search recipe button")
        }
    }
}




@Composable
fun MainScreenFloatingButton(
    modifier: Modifier,
){

    val gradientBrush = remember {
        Brush.linearGradient(
            0.0f to Color(195, 197, 82),
            1.0f to Color(255, 210, 251),
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
    contentListState: LazyListState
){

    val gradientBrush = remember {
        Brush.linearGradient(
            0.0f to Color(149, 219, 0),
            1.0f to Color(84, 168, 148),
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

    val deskFullScreen = remember {
        derivedStateOf {
            if (contentListState.firstVisibleItemIndex == 0){
                contentListState.firstVisibleItemScrollOffset > 200f
            } else {
                true
            }
        }
    }

    val deskSizeAnimation by animateFloatAsState(
        targetValue = if (deskFullScreen.value) 1f else 0f,
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
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        ContentTittle(scrollState = { tittleVisibilityState.value })
        RecipesList(
            modifier = Modifier,
            listState = contentListState
        )
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
    modifier: Modifier,
    listState: LazyListState
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ){
        items(10){
            RecipeItem(modifier = Modifier.padding(top = 36.dp))
        }
    }
}

@Composable
fun RecipeItem(
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
            RecipeCardBackground()
            RecipeInfo(modifier = Modifier
                .padding(
                    start = 18.dp,
                    bottom = 10.dp
                )
                .align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
fun RecipeCardBackground(){
    val whiteGradient = remember {
        Brush.verticalGradient(
            0.0f to Color.Transparent,
            0.84f to Color(217, 217, 217
            )
        )
    }

    Image(
        modifier = Modifier
            .fillMaxSize()
            .drawWithContent {
                this.drawContent()
                this.drawRect(whiteGradient)
            },
        painter = painterResource(id = R.drawable.img),
        contentDescription = "Recipe img",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun RecipeTittle(
    modifier: Modifier
){
    Text(
        modifier = modifier
            .padding(bottom = 16.dp),
        text = "Блины на молоке",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color(108, 153, 35)
    )
}

@Composable
fun RecipeInfo(
    modifier: Modifier
){
    Box(modifier = modifier){
        RecipeTittle(
            modifier = Modifier
                .align(Alignment.TopStart)
        )
        RecipeAdditionalInfo(
            modifier = Modifier
                .align(Alignment.BottomStart)
        )
    }
}


@Composable
fun RecipeAdditionalInfo(
    modifier: Modifier
){
    Row(
        modifier = modifier
    ){
        ComplexityIcon()
        ComplexityTittle(modifier = Modifier.padding(start = 4.dp))
        CaloriesIcon(modifier = Modifier.padding(start = 10.dp))
        CaloriesAmount(modifier = Modifier.padding(start = 4.dp))
        TimeIcon(modifier = Modifier.padding(start = 10.dp))
        TimeAmount(modifier = Modifier.padding(start = 4.dp))
    }
}

@Composable
fun ComplexityIcon(){
    Image(
        modifier = Modifier
            .size(14.dp, 14.dp),
        painter = painterResource(id = R.drawable.complexity_hard),
        contentDescription = "",
    )
}

@Composable
fun ComplexityTittle(
    modifier: Modifier = Modifier
){
    Text(
        modifier = modifier,
        text = "Сложно",
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        color = Color.Black
    )
}

@Composable
fun CaloriesIcon(
    modifier: Modifier = Modifier
){
    Image(
        modifier = modifier
            .size(14.dp, 14.dp),
        painter = painterResource(id = R.drawable.calories_icon),
        contentDescription = "",
    )
}

@Composable
fun CaloriesAmount(
    modifier: Modifier = Modifier
){
    Text(
        modifier = modifier,
        text = "271 кал.",
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        color = Color.Black
    )
}

@Composable
fun TimeIcon(
    modifier: Modifier = Modifier
){
    Image(
        modifier = modifier
            .size(14.dp, 14.dp),
        painter = painterResource(id = R.drawable.clock_icon),
        contentDescription = "",
    )
}

@Composable
fun TimeAmount(
    modifier: Modifier = Modifier
){
    Text(
        modifier = modifier,
        text = "30 мин",
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        color = Color.Black
    )
}


