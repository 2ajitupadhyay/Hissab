package com.ajidroid.hissab.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ajidroid.hissab.R
import com.ajidroid.hissab.ui.navigation.HissabNavGraph

@Composable
fun HissabApp(navController: NavHostController = rememberNavController()) {
    HissabNavGraph(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HissabTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    showNavigationIcon: Boolean = false,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigateUp: () -> Unit = {},
    showActionIcon: Boolean = false,
    actionIcon: ImageVector = Icons.Filled.Create,
    onActionClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,

        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
        },

        navigationIcon = {
            if (showNavigationIcon) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = stringResource(R.string.back_button),
                    )
                }
            }
        },

        actions = {
            if (showActionIcon) {
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = stringResource(R.string.action),
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
//            titleContentColor = MaterialTheme.colorScheme.onSurface,
//            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
//            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}