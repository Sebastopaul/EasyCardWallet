package com.isitechproject.easycardwallet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme
import com.isitechproject.easycardwallet.screens.home.HomeScreen
import com.isitechproject.easycardwallet.screens.sign_in.SignInScreen
import com.isitechproject.easycardwallet.screens.sign_up.SignUpScreen
import com.isitechproject.easycardwallet.screens.splash.SplashScreen

@Composable
fun EasyCardWalletApp() {
    EasyCardWalletTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    notesGraph(appState)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        EasyCardWalletAppState(navController)
    }

fun NavGraphBuilder.notesGraph(appState: EasyCardWalletAppState) {
    composable(MAIN_SCREEN) {
        HomeScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
        )
    }

    composable(SIGN_IN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun EasyCardWalletAppPreview() {
    EasyCardWalletApp()
}