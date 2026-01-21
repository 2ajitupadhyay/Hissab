package com.ajidroid.hissab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ajidroid.hissab.ui.home.HomeScreen
import com.ajidroid.hissab.ui.member.MemberDetailScreen

@Composable
fun HissabNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {

        composable(
            route = HomeDestination.route
        ) {
            HomeScreen(
                navigateToMemberEntry = {
                    // future: navController.navigate(MemberEntryDestination.route)
                },
                navigateToMemberDetail = { memberId ->
                    navController.navigate(
                        MemberDetailDestination.createRoute(memberId)
                    )
                }
            )
        }

        composable(
            route = MemberDetailDestination.route,
            arguments = listOf(
                navArgument(MemberDetailDestination.MEMBER_ID_ARG) {
                    type = NavType.IntType
                }
            )
        ) {backStackEntry ->
            val memberId =
                backStackEntry.arguments
                    ?.getInt(MemberDetailDestination.MEMBER_ID_ARG)
                    ?: return@composable

            MemberDetailScreen(
                memberId = memberId,
                onNavigateUp = { navController.popBackStack() }
            )
        }
    }
}