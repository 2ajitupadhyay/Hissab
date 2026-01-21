package com.ajidroid.hissab.ui.navigation

sealed interface HissabDestination { // Make it sealed Interface and put all the destination into one composable
    // :For large apps, sealed + same package is preferred.
    val route: String
}