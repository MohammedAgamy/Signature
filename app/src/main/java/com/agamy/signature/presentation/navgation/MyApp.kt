package com.agamy.signature.presentation.navgation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agamy.signature.presentation.draw.DrawScreen
import com.agamy.signature.presentation.splash.SplashScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination =Routes.SPLASH
    ){
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }
        composable(Routes.HOME) {
            DrawScreen()
        }
    }
}