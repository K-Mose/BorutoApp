package com.mose.kim.borutoapp.presentation.screens.welcome

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.mose.kim.borutoapp.R
import com.mose.kim.borutoapp.domain.model.OnBoardingPage
import com.mose.kim.borutoapp.ui.theme.*
import com.mose.kim.borutoapp.util.Constants

@ExperimentalPagerApi
@Composable
fun WelcomeScreen(navController: NavHostController) {

    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )

    val pagerState = rememberPagerState()
    
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colors.welcomeScreenBackgroundColor)
    ) {
        // 페이저
        HorizontalPager(
            modifier = Modifier.weight(10f), // weight로 영역 지정
            count = Constants.ON_BOARD_PAGE_COUNT,
            state = pagerState,
            verticalAlignment = Alignment.Top,
        ) { // content lambda
            position ->
            PagerScreen(onBoardingPage = pages[position])
        }
        // 인디케이터
        HorizontalPagerIndicator(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            activeColor = MaterialTheme.colors.activeIndicatorColor,
            inactiveColor = MaterialTheme.colors.inactiveIndicatorColor,
            indicatorWidth = PAGING_INDICATOR_WIDTH,
            spacing = PAGING_INDICATOR_SPACING
        )
        FinishButton(pagerState = pagerState, modifier = Modifier.weight(1f)) {

        }
    }
}


@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.7f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = stringResource(R.string.on_boarding_image)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPage.title,
            color = MaterialTheme.colors.titleColor,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = EXTRA_LARGE_PADDING)
                .padding(top = SMALL_PADDING),
            text = onBoardingPage.description,
            color = MaterialTheme.colors.descriptionColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalPagerApi
@Composable
fun FinishButton(
    modifier: Modifier, // weight는 columnScope의 modifier에서만 사용 가능 // https://stackoverflow.com/questions/73271265/modifier-weight-not-available-in-jetpack-compose-1-1-1
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(modifier = modifier
        .padding(horizontal = EXTRA_LARGE_PADDING),
    verticalAlignment = Alignment.Top,
    horizontalArrangement = Arrangement.Center) {
        // pagerState가 마지막 일 때만 작동하도록 animateVisibility 사용
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth(),
            visible = pagerState.currentPage == 2 // page가 2일 때만
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.buttonBackgroundColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Finish")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun Pager1() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.First)
    }
}

@Preview(showBackground = true)
@Composable
fun Pager2() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Second)
    }
}

@Preview(showBackground = true)
@Composable
fun Pager3() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Third)
    }
}