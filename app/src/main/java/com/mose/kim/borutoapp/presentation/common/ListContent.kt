package com.mose.kim.borutoapp.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
//import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.mose.kim.borutoapp.R
import com.mose.kim.borutoapp.domain.model.Hero
import com.mose.kim.borutoapp.navigation.Screen
import com.mose.kim.borutoapp.presentation.components.RatingWidget
import com.mose.kim.borutoapp.ui.theme.*
import com.mose.kim.borutoapp.util.Constants.BASE_URL

@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navController: NavHostController
) {
    heroes.itemCount
    navController.context
}

@ExperimentalCoilApi
@Composable
fun HeroItem(
    hero: Hero,
    navController: NavHostController // 아이템 클릭 시 디테일 페이지로 이동시키기위해
) {
//    val painter = rememberImagePainter(data = "$BASE_URL${hero.image}", builder = {
//        placeholder(R.drawable.placeholder)
//        error(R.drawable.placeholder)
//    })
    val painter = rememberAsyncImagePainter(model = ImageRequest
        .Builder(LocalContext.current)
        .data("$BASE_URL${hero.image}").apply {
            placeholder(R.drawable.placeholder)
            error(R.drawable.placeholder)
        }.build()
    )

    
    Box(
        modifier = Modifier
            .height(HERO_ITEM_HEIGHT)
            .clickable { // click Listener
                navController.navigate(Screen.Details.passHeroId(heroId = hero.id))
            },
        contentAlignment = Alignment.BottomStart
    ) {
        // 배경 이미지
        Surface(shape = Shapes.large) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = stringResource(R.string.hero_image),
                contentScale = ContentScale.Crop
            )
        }

        Surface(modifier = Modifier
            .fillMaxHeight(0.4f)
            .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = hero.name,
                    color = MaterialTheme.colors.titleColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = hero.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        rating = hero.rating
                    )
                    Text(
                        text = "(${hero.rating})",
                        textAlign =TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Preview
@Composable
private fun HeroItemPreview() {
    HeroItem(
        hero = Hero(
            id = 1,
            name = "NAME",
            image = "",
            about = "about something else",
            rating = 4.2,
            power = 99,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureType = listOf()
        ),
        navController = rememberNavController()
    )
}