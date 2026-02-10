package com.ajidroid.hissab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ajidroid.hissab.auth.presentation.LoginRoute
import com.ajidroid.hissab.ui.HissabApp
import com.ajidroid.hissab.ui.start.SplashScreen
import com.ajidroid.hissab.ui.start.StartDestination
import com.ajidroid.hissab.ui.start.StartViewModel
import com.ajidroid.hissab.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    HissabApp()
                    val startViewModel: StartViewModel = hiltViewModel()
                    val destination by startViewModel.startDestination.collectAsState()

                    when (destination) {
                        StartDestination.Splash -> SplashScreen()
                        StartDestination.Login -> LoginRoute()
                        StartDestination.Home -> HissabApp()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@Preview(showBackg round = true)
//@Composable
//fun GreetingPreview() {
//    HissabTheme {
//        Greeting("Android")
//    }
//}