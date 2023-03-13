package com.mose.kim.borutoapp.presentation.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun HomeScreen(
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
    }
}