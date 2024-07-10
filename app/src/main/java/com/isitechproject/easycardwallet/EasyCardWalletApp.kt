package com.isitechproject.easycardwallet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardslistscreen.LoyaltyCardsListScreen
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme
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
                    easyCardWalletGraph(appState)
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

fun NavGraphBuilder.easyCardWalletGraph(appState: EasyCardWalletAppState) {
    composable(LOYALTY_CARDS_LIST_SCREEN) {
        LoyaltyCardsListScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(SIGN_IN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(
                route,
                popUp
            )
        })
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(
                route,
                popUp
            )
        })
    }

    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(
                route,
                popUp
            )
        })
    }
}