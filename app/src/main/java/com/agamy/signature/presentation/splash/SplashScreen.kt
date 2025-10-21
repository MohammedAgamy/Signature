package com.agamy.signature.presentation.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.agamy.signature.R
import com.agamy.signature.presentation.navgation.Routes
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@Composable
fun SplashScreen (navController: NavController) {
    //lottie animation
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.arttt)
    )

    LaunchedEffect(Unit) {
            delay(3000)
            navController.navigate(Routes.HOME)


    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // display the lottie animation
        LottieAnimation(
            alignment = Alignment.Center,
            modifier = Modifier.size(400.dp),
            composition = composition
        )
    }
}