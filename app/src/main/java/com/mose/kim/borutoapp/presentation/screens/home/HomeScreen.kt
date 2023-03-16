package com.mose.kim.borutoapp.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.mose.kim.borutoapp.presentation.common.ListContent
import com.mose.kim.borutoapp.presentation.components.RatingWidget
import com.mose.kim.borutoapp.ui.theme.EXTRA_LARGE_PADDING

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allHeroes = homeViewModel
        .getAllHeroes
        .collectAsLazyPagingItems() // PagingData를 Flow로 모아 LazyPagingItems의 객체로 표시
                                    // LazyColumn에 쉽게 값을 넘김

    Scaffold(
        topBar = {
            HomeTopBar (onSearchClicked = {})
        }
    ) {
        it
        ListContent(
            heroes = allHeroes,
            navController = navController
        )
    }
}