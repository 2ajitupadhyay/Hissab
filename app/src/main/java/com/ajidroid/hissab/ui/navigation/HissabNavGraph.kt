package com.ajidroid.hissab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ajidroid.hissab.ui.home.HomeDestination
import com.ajidroid.hissab.ui.home.HomeScreen

@Composable
fun HissabNavHost (
    navController : NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToMemberEntry = {},
                navigateToMemberUpdate = {}
            )
        }
    }
}