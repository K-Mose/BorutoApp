package com.mose.kim.borutoapp.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen")
    object Welcome: Screen("welcome_screen")
    object Home: Screen("home_screen")
    // https://developer.android.com/jetpack/compose/navigation#nav-with-args
    // passing specific argument
    object Details: Screen("details_screen/{heroId}") {
        fun passHeroId(heroId: Int): String {
            return "details_screen/$heroId"
        }
    }
    object Search: Screen("search_screen")
}
