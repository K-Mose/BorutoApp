package com.mose.kim.borutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mose.kim.borutoapp.presentation.screens.home.HomeScreen
import com.mose.kim.borutoapp.presentation.screens.splash.SplashScreen
import com.mose.kim.borutoapp.presentation.screens.welcome.WelcomeScreen
import com.mose.kim.borutoapp.util.Constants

@ExperimentalPagerApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController,
    startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(Constants.DETAILS_AGRUMENT_KEY){
                type = NavType.IntType
            })
        ) {

        }
        composable(route = Screen.Search.route) {

        }

    }
}