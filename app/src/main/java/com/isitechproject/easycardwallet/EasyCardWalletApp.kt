package com.isitechproject.easycardwallet

import android.app.ActivityManager
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.isitechproject.easycardwallet.screens.auth.sign_in.SignInScreen
import com.isitechproject.easycardwallet.screens.auth.sign_up.SignUpScreen
import com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardslistscreen.LoyaltyCardsListScreen
import com.isitechproject.easycardwallet.screens.splash.SplashScreen
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme
import org.checkerframework.checker.units.qual.Current


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
    composable(CREATE_LOYALTY_CARD_SCREEN) {
        EasyCardWalletActivity().switchActivity(BarcodeScannerActivity::class.java)
        //BarcodeScannerActivity().switchActivity(EasyCardWalletActivity::class.java)
        //CreateLoyaltyCardScreen(
        //    restartApp = { route -> appState.clearAndNavigate(route) },
        //    openScreen = { route -> appState.navigate(route) }
        //)
    }

    composable(
        route = "$LOYALTY_CARD_SCREEN$LOYALTY_CARD_ID_ARG",
        arguments = listOf(navArgument(LOYALTY_CARD_ID) { defaultValue = LOYALTY_CARD_DEFAULT_ID })
    ) {
    }

    composable(LOYALTY_CARDS_LIST_SCREEN) {
        LoyaltyCardsListScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openAddScreen = { route -> appState.navigate(route) }
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