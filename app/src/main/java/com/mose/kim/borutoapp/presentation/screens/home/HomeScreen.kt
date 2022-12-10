package com.mose.kim.borutoapp.presentation.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            HomeTopBar (onSearchClicked = {})
        }
    ) {
        it
    }
}